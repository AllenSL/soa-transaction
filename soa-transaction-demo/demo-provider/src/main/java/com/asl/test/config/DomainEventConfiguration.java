package com.asl.test.config;

import cn.com.gome.cloud.openplatform.diamond.DiamondOP;
import cn.com.pop.domain.event.consumer.SpringDomainEventConsumer;
import cn.com.pop.domain.event.log.KafkaAndLogMqPublishLogger;
import cn.com.pop.domain.event.log.KafkaAndLogMqReceiveLogger;
import cn.com.pop.domain.event.publisher.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventConfiguration {

    private String rocketMqServers = DiamondOP.getProperty("NPOP_EVENT_CDC", "CANAL_CONFIG", "namesrvAddr", "");

    @Autowired
    private ApplicationContext applicationContext;

    //消息发送端需要配置
    @Bean
    public DomainEventPublisher domainEventPublisher() {

        KafkaAndLogMqPublishLogger kafkaMqPublishLogger = new KafkaAndLogMqPublishLogger();

        DomainEventPublisher domainEventPublisher = new DomainEventPublisher(rocketMqServers,"bhs-channel-dubbo");
        domainEventPublisher.setMqPublishLog(kafkaMqPublishLogger); //如果需要记录发送日志需要设置
        domainEventPublisher.setDateFormatString("yyyy/MM/dd HH:mm:ss"); //日期的转化格式
        domainEventPublisher.start();
        return domainEventPublisher;
    }

    //消息消费端需要配置
    @Bean
    public SpringDomainEventConsumer domainEventConsumer(){

        KafkaAndLogMqReceiveLogger kafkaMqReceiveLogger = new KafkaAndLogMqReceiveLogger();

        SpringDomainEventConsumer springDomainEventConsumer = new SpringDomainEventConsumer(applicationContext,rocketMqServers,"bhs-channel-business");
        springDomainEventConsumer.setDateFormatString("yyyy/MM/dd HH:mm:ss");
//        springDomainEventConsumer.setFlowAdjuster(gcacheFlowAdjuster); //如需要消息去重需要配置
        springDomainEventConsumer.registerMqReceiveLogger(kafkaMqReceiveLogger); //如需要记录响应日志需要配置
        springDomainEventConsumer.start();
        return springDomainEventConsumer;
    }

}
