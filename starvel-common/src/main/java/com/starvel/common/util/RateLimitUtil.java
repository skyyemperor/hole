//
// Copyright (c) 2020 山东大学学生在线. All rights reserved.
//

package com.starvel.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 限流工具
 * 主要用于登录时的暴力破解防护
 *
 * @author Zsj
 */
@Component
public class RateLimitUtil {

    private static final String MEMBER = "rate_limit";
    private static final String UNLIMITED = "unlimited";

    @Autowired
    private LockUtil lockUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 默认的限流配置
     */
    private RateConfig rateConfig = RateConfig.SYSTEM_LOGIN;

    public RateLimitUtil() {

    }

    public void setRateConfig(RateConfig rateConfig) {
        this.rateConfig = rateConfig;
    }

    public int checkRateLimit(String key) {
        Double score = stringRedisTemplate.opsForZSet().score(key, MEMBER);
        return score == null ? -1 : score.intValue();
    }

    /**
     * 增加密码错误的次数
     * 若输入错误超过5次，则10分钟内无法再次进行登录操作。使用默认的配置。
     *
     * @param key 用户ID
     * @see RateLimitUtil#incrementTimes(String, RateConfig)
     */
    public void incrementTimes(String key) {
        incrementTimes(key, rateConfig);
    }

    /**
     * 增加密码错误的次数
     * 若输入错误超过5次，则10分钟内无法再次进行登录操作。
     *
     * @param key    用户ID
     * @param config 限流配置
     */
    public void incrementTimes(String key, RateConfig config) {
        lockUtil.lockOrWait("RATE-LOCK:" + key);

        // 查看当前输入错误的次数
        int check = checkRateLimit(key);
        if (check < 0) {
            // 不存在记录
            stringRedisTemplate.opsForZSet().add(key, MEMBER, 0);
            stringRedisTemplate.opsForZSet().add(key, UNLIMITED, 1);
            stringRedisTemplate.expire(key, 60, TimeUnit.SECONDS);
        } else if (check > config.getAllowedTimes()) {
            // 已经超过了错误次数限制
            // 检查是否已经被限制登录
            Double unlimited = stringRedisTemplate.opsForZSet().score(key, UNLIMITED);
            if (unlimited != null && unlimited > 0) {
                // 如果没有被限制登录（通过UNLIMITED键来标识是否不受限制）
                // 进行登录限制
                stringRedisTemplate.expire(key, config.getFailedWaitingSeconds(), TimeUnit.SECONDS);
                // 删除不受限的标志
                stringRedisTemplate.opsForZSet().remove(key, UNLIMITED);
            }
        } else {
            // 未到达错误次数的阈值
            // 增加次数，并设置1分钟的超时
            stringRedisTemplate.opsForZSet().incrementScore(key, MEMBER, 1);
            stringRedisTemplate.expire(key, 60, TimeUnit.SECONDS);
        }
        lockUtil.releaseKey("RATE-LOCK:" + key);
    }

    public long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 删除访问限制
     *
     * @param key 用户ID
     */
    public void removeRateLimit(String key) {
        stringRedisTemplate.opsForZSet().remove(key, MEMBER);
        stringRedisTemplate.opsForZSet().remove(key, UNLIMITED);
    }

    /**
     * 限流配置
     */
    public static class RateConfig {
        /**
         * 默认允许的尝试次数
         */
        private int allowedTimes = 5;
        /**
         * 多次尝试失败后的等待时间
         */
        private int failedWaitingSeconds = 600;

        public RateConfig() {
        }

        public RateConfig(int allowedTimes, int failedWaitingSeconds) {
            this.allowedTimes = allowedTimes;
            this.failedWaitingSeconds = failedWaitingSeconds;
        }

        /**
         * 默认配置为限制系统登录的配置，不允许过多访问/login/system接口
         */
        public static final RateConfig SYSTEM_LOGIN = new RateConfig();

        public int getAllowedTimes() {
            return allowedTimes;
        }

        public void setAllowedTimes(int allowedTimes) {
            this.allowedTimes = allowedTimes;
        }

        public int getFailedWaitingSeconds() {
            return failedWaitingSeconds;
        }

        public void setFailedWaitingSeconds(int failedWaitingSeconds) {
            this.failedWaitingSeconds = failedWaitingSeconds;
        }
    }
}
