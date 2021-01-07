package com.starvel.common.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public RedisUtil() {

    }

    public String get(String key) {
        if (stringRedisTemplate.hasKey(key)) {
            return stringRedisTemplate.opsForValue().get(key);
        }
        return "";
    }

    public void set(String key, String value, long expire) {
        stringRedisTemplate.opsForValue().set(key, value, expire, TimeUnit.MILLISECONDS);
    }

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public StringRedisTemplate getTemplate() {
        return stringRedisTemplate;
    }

    public <T> T getObject(String key, Class<T> clazz) {
        String res = get(key);
        if ("".equals(res))
            return null;
        else {
            return JSON.parseObject(res, clazz);
        }
    }

    public void setObject(String key, Object obj, long expire) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(obj),
                expire, TimeUnit.MILLISECONDS);
    }
}
