package com.asl.soatransaction.logic.spring;

import com.asl.soatransaction.annotation.SOACommit;
import com.asl.soatransaction.annotation.SOARollBack;
import com.asl.soatransaction.annotation.SOAService;
import com.asl.soatransaction.logic.SOARollbackMeta;
import com.asl.soatransaction.logic.exp.SOATransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * dubbo接口调用类处理器
 * @author ansonglin
 */
public class SOATransactionBeanProcessor implements ApplicationContextAware {

    Logger LOGGER = LoggerFactory.getLogger(SOATransactionBeanProcessor.class);
    private ApplicationContext applicationContext;
    /**
     * method -> rollbackMeta
     */
    public static final Map<Method,SOARollbackMeta> METHOD_ROLLBACK_MAPPING = new HashMap<>();
    /**
     * class ->(method -> parameterType)
     */
    public static final Map<Class<?>,Map<String,Class<?>[]>> CLASS_PARAMETERTYPE_MAPPING = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 将@SOACommit标记的方法和回滚的方法绑定
     */
    public void init(){
        Map<String, Object> beanMaps = applicationContext.getBeansWithAnnotation(SOAService.class);
        if(!ObjectUtils.isEmpty(beanMaps)){
            Iterator<Map.Entry<String, Object>> iterator = beanMaps.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> bean = iterator.next();
                Object beanClz = bean.getValue();
                Class cls =  this.getWithAnnotationClass(beanClz.getClass(),SOAService.class);
                //获取接口中所有方法
                Method[] methods = cls.getMethods();
                    for (Method method : methods) {
                        try {
                            if (method.isAnnotationPresent(SOACommit.class)) {
                                SOACommit annotation = method.getAnnotation(SOACommit.class);
                                String rollBackMethodName = annotation.rollBackMethod();
                                int[] value = annotation.value();
                                this.checkRollMeta(rollBackMethodName);
                                SOARollbackMeta rollbackMeta = new SOARollbackMeta(cls,rollBackMethodName,value);
                                METHOD_ROLLBACK_MAPPING.put(method,rollbackMeta);
                            }
                            if(method.isAnnotationPresent(SOARollBack.class)){
                                this.cacheClassAndMethodParameterTypes(method,cls);
                            }
                        } catch (Exception e) {
                            LOGGER.error("dubbo interface parse error " + cls + "."+method,e);
                            throw new SOATransactionException(SOATransactionException.INTERFACE_PARSE_EXCEPTION,"dubbo interface parse error " + cls + "."+method,e);
                        }
                    }
            }
        }
    }


    private void cacheClassAndMethodParameterTypes(Method method,Class<?> cls){
        Map<String, Class<?>[]> cacheMap = CLASS_PARAMETERTYPE_MAPPING.get(cls);
        if(ObjectUtils.isEmpty(cacheMap)){
            cacheMap = new HashMap<>();
        }
        String methodName = method.getName();
        cacheMap.put(methodName,method.getParameterTypes());
        CLASS_PARAMETERTYPE_MAPPING.put(cls,cacheMap);
    }


    private void checkRollMeta(String rollBackMethodName) {
        if (rollBackMethodName.isEmpty()) {
            throw new SOATransactionException(SOATransactionException.CHECK_EXCEPTION,"rollBackMethod check error");
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
