package com.asl.test.config;

import cn.com.gome.cloud.openplatform.diamond.DiamondOP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.Gcache;
import redis.GcacheClient;

/**
 * @author Zhang Junshu @gome.com.cn
 * @since 2018-06-28 3:56 PM
 */
@Configuration
public class GCacheConfiguration {

    private String zookeeperAddress = DiamondOP.getProperty("NPOP_SBG", "PROP_DATA", "gcache.zookeeperAddress", "");

    private String businessName = DiamondOP.getProperty("NPOP_SBG", "CHANNEL_CONF", "gcache.businessName", "");



    @Bean
    public Gcache getGomeCache() {
        return new GcacheClient(this.zookeeperAddress, businessName);
    }
}