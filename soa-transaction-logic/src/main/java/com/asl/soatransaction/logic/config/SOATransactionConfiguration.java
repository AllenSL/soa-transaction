package com.asl.soatransaction.logic.config;

import com.asl.soatransaction.logic.CacheWrapper;
import com.asl.soatransaction.logic.aop.SOATransactionAspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author ansonglin
 */
@Configuration
public class SOATransactionConfiguration {


    @Bean
    public SOATransactionAspect soaTransactionAspect(CacheWrapper cacheWrapper, ApplicationContext applicationContext){
        return new SOATransactionAspect(cacheWrapper,applicationContext);
    }

    @Bean
    public CacheWrapper cacheWrapper(StringRedisTemplate stringRedisTemplate){
        return new CacheWrapper(stringRedisTemplate);
    }
}
