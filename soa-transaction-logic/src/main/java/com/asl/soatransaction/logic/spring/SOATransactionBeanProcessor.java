package com.asl.soatransaction.logic.spring;

import com.asl.soatransaction.annotation.SOACommit;
import com.asl.soatransaction.annotation.SOAService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ansonglin
 */
public class SOATransactionBeanProcessor implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private static final Map<String,String> map = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 将@SOACommit标记的方法和回滚的方法绑定
     */
    public void init(){
        Map<String, Object> beanMaps = applicationContext.getBeansWithAnnotation(SOAService.class);
        if(ObjectUtils.isEmpty(beanMaps)){
            Iterator<Map.Entry<String, Object>> iterator = beanMaps.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> bean = iterator.next();
                Object beanClz = bean.getValue();
                Class cls =  this.getWithAnnotationClass(beanClz.getClass(),SOAService.class);
                //获取接口中所有方法
                Method[] methods = cls.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(SOACommit.class)) {
                        SOACommit annotation = method.getAnnotation(SOACommit.class);
                        String rollBackMethod = annotation.rollBackMethod();
                        String args = annotation.args();
                        int[] value = annotation.value();

                    }
                }
                
            }
        }
    }



    public Class<?> getWithAnnotationClass(Class<?> clz, Class<? extends Annotation> annotationType){
        Class<?>[] interfaces = clz.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            if(anInterface.isAnnotationPresent(annotationType)){
                 return anInterface;
            }
        }
        return null;
    }
}
