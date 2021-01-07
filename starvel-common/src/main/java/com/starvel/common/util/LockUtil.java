//
// Copyright (c) 2020 山东大学学生在线. All rights reserved.
//

package com.starvel.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class LockUtil {
    /**
     * 锁过期时间，防止因为异常出现死锁
     */
    private static final Long EXPIRE_TIME = 5000L;
    /**
     * 争抢锁的自旋等待时间
     */
    private static final Long WAIT_TIME = 100L;
    /**
     * 解锁的LUA脚本
     */
    private static final String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) " +
            "else return 0 end";

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 加锁操作
     * 如果无法获取到锁则进行自旋
     * 可以适当调整{@link LockUtil#WAIT_TIME}的自旋时长
     *
     * @param key 锁的KEY
     */
    public void lockOrWait(String key) {
        while (!tryLock(key)) {
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
    }

    /**
     * 释放锁操作
     * 执行LUA脚本（{@link LockUtil#UNLOCK_LUA}）来保证原子性
     *
     * @param key 锁的KEY
     */
    public void releaseKey(String key) {
        unlock(key);
    }

    /**
     * 尝试加锁
     * 用到了Redis的set nx操作，即不存在则设置
     *
     * @param key 锁的KEY
     * @return 是否成功加锁
     */
    private boolean tryLock(String key) {
        return redisUtil.getTemplate().execute((RedisCallback<Boolean>) redisConnection ->
                redisConnection.set(key.getBytes(), "val".getBytes(), Expiration.milliseconds(EXPIRE_TIME),
                        RedisStringCommands.SetOption.SET_IF_ABSENT));
    }

    /**
     * 释放锁
     *
     * @param key 锁的KEY
     */
    private void unlock(String key) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new StaticScriptSource(UNLOCK_LUA));
        redisUtil.getTemplate().execute(script, Collections.singletonList(key), "val");
    }

}
