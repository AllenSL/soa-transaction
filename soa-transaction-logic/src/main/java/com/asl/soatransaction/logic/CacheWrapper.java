package com.asl.soatransaction.logic;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author ansonglin
 */
public class CacheWrapper {

    private StringRedisTemplate stringRedisTemplate;

    public CacheWrapper(StringRedisTemplate redisTemplate){
        this.stringRedisTemplate = redisTemplate;
    }

    public String get(String key){
       return stringRedisTemplate.opsForValue().get(key);
    }

    public void setex(String key,Object value,long seconds){
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value),seconds);
    }

    public void del(String key){
        stringRedisTemplate.delete(key);
    }
}
