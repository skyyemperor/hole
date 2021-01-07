package com.starvel.common.util;

import com.starvel.common.config.StarvelConfig;
import com.starvel.common.constant.BaseValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenUtil {
    private static final String APPLICATION_NAME = BaseValue.PROJECT_NAME;

    private static final String USER_TOKEN_KEY = APPLICATION_NAME + ":TOKEN_KEY:%s:USER:%s";
    private static final String USER_REFRESH_KEY = APPLICATION_NAME + ":REFRESH_KEY:%s:USER:%s";
    private static final String TOKEN_USER_KEY = APPLICATION_NAME + ":TOKEN:%s";
    private static final String REFRESH_USER_KEY = APPLICATION_NAME + ":REFRESH:%s";
    private static final long TOKEN_EXPIRE_TIME = 5 * 24 * 3600 * 1000L; //5天
    private static final long REFRESH_EXPIRE_TIME = 30 * 24 * 3600 * 1000L; //一个月

    private static ConcurrentHashMap<String, Lock> lockMap = new ConcurrentHashMap<>();

    @Autowired
    private RedisUtil redisUtil;

    public String[] generateToken(String id, String k) {
        newSession(id);

        String[] result;
        synchronized (lockMap.get(id)) {
            String[] tmpToken = getToken(id, k);
            if (tmpToken.length == 2) {
                result = tmpToken;
            } else {
                //token已过期或未登录
                String uuid = UUID.randomUUID().toString();
                String token = encodeByMd5(id, k, uuid) + "-" + System.currentTimeMillis() % 1000000;
                String refreshToken = encodeByMd5(id, k, uuid, "refresh") + "-" + System.currentTimeMillis() % 1000000;

                //删除原来的data，之后保存至redis
                removeOldData(id, k);
                saveToRedis(id, k, token, refreshToken);
                result = new String[]{token, refreshToken};
            }
        }

        exitSession(id);
        return result;
    }


    public String[] refresh(String refreshToken, String k) {
        String id = getIdByRefreshToken(refreshToken);

        if (id.isEmpty()) {
            return new String[]{};
        } else {
            return generateToken(id, k);
        }
    }

    public String validate(String token) {
        return redisUtil.get(String.format(TOKEN_USER_KEY, token));
    }


    /**
     * 获取token和refreshToken
     *
     * @param id
     */
    private String[] getToken(String id, String k) {
        String token = redisUtil.get(String.format(USER_TOKEN_KEY, k, id));
        String refreshToken = redisUtil.get(String.format(USER_REFRESH_KEY, k, id));

        if ("".equals(token) || "".equals(refreshToken)) {
            return new String[]{};
        } else {
            return new String[]{token, refreshToken};
        }
    }

    private void saveToRedis(String id, String k, String token, String refreshToken) {
        redisUtil.set(String.format(TOKEN_USER_KEY, token), id, TOKEN_EXPIRE_TIME);
        redisUtil.set(String.format(USER_TOKEN_KEY, k, id), token, TOKEN_EXPIRE_TIME);
        redisUtil.set(String.format(REFRESH_USER_KEY, refreshToken), id, REFRESH_EXPIRE_TIME);
        redisUtil.set(String.format(USER_REFRESH_KEY, k, id), refreshToken, REFRESH_EXPIRE_TIME);
    }

    private void removeOldData(String id, String k) {
        String token = redisUtil.get(String.format(USER_TOKEN_KEY, k, id));
        if (!"".equals(token)) {
            redisUtil.delete(String.format(USER_TOKEN_KEY, k, id));
            redisUtil.delete(String.format(TOKEN_USER_KEY, token));
        }

        String refreshToken = redisUtil.get(String.format(USER_REFRESH_KEY, k, id));
        if (!"".equals(refreshToken)) {
            redisUtil.delete(String.format(USER_REFRESH_KEY, k, id));
            redisUtil.delete(String.format(REFRESH_USER_KEY, refreshToken));
        }
    }

    private String getIdByRefreshToken(String refreshToken) {
        return redisUtil.get(String.format(REFRESH_USER_KEY, refreshToken));
    }

    private String encodeByMd5(Object... args) {
        String data = Arrays.toString(args);
        try {
            byte[] bytes = MessageDigest.getInstance("MD5").digest(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                if ((b & 0xff) < 16)
                    sb.append("0");
                sb.append(Long.toString(b & 0xff, 16));
            }
            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    private synchronized void newSession(String id) {
        lockMap.putIfAbsent(id, new Lock());
        lockMap.get(id).newSession();
    }

    private synchronized void exitSession(String id) {
        lockMap.get(id).exitSession();
        if (lockMap.get(id).session == 0) {
            lockMap.remove(id);
        }
    }

    private static class Lock {
        private int session;

        Lock() {
            session = 0;
        }

        public synchronized int getSession() {
            return session;
        }

        public synchronized void newSession() {
            session++;
        }

        public synchronized void exitSession() {
            session--;
        }
    }
}
