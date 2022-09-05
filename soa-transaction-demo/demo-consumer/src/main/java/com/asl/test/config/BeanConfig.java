package com.asl.test.config;

import com.asl.soatransaction.logic.spring.SOATransactionBeanProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {


    @Bean(initMethod = "init")
    public SOATransactionBeanProcessor soaTransactionBeanProcessor(){
     return new SOATransactionBeanProcessor();
    }

}
