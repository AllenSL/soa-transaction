package com.asl.test.config;

import cn.com.gome.cloud.openplatform.diamond.DiamondOP;
import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.gome.architect.transaction.spring.SOATransactionBeanProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yangyu-ds on 2018/9/19.
 */
@Configuration
public class DubboConfig {

    /**
     * 获取dubbo注册的地址
     */
    private static String defaultZookeeperAddress = DiamondOP.getProperty("NPOP_POPCOMMON", "SPRING_XML_PROPERTIES_CONF", "defaultZookeeperAddress", "");

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("test-d");
        application.setLogger("slf4j");
        return application;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress(defaultZookeeperAddress);
        return registry;
    }
    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(-1);
        return protocol;
    }

    @Bean
    public AnnotationBean dubboAnnotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage("com.asl.test");
        return annotationBean;
    }

    /**
     * 配置全局provider属性，延迟暴露接口
     * @return
     */
    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        //初始化完成之后再暴露接口
        providerConfig.setDelay(-1);
        providerConfig.setTimeout(30000);
        return providerConfig;
    }

    /**
     * 配置全局consumer属性
     * @return
     */
    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(false);
        return consumerConfig;
    }


    @Bean(initMethod = "init")
    public SOATransactionBeanProcessor soaTransactionBeanProcessor(){
        return new SOATransactionBeanProcessor();
    }
}
