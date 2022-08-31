package com.asl.soatransaction.logic;

import com.alibaba.fastjson.JSON;
import redis.Gcache;

/**
 * @author ansonglin
 */
public class CacheWrapper {

    private Gcache gcache;

    public CacheWrapper(Gcache gcache){
        this.gcache = gcache;
    }

    public String get(String key){
       return gcache.get(key);
    }

    public void setex(String key,Object value,int seconds){
        gcache.setex(key,seconds,JSON.toJSONString(value));
    }

    public void del(String key){
        gcache.del(key);
    }
}
