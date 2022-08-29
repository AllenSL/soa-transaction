package com.asl.soatransaction.annotation;


import java.lang.annotation.*;

/**
 * 提交事务注解
 * @author ansonglin
 */
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SOACommit {

    /**
     * 事务回滚回调方法名称
     * @return
     */
    String rollBackMethod() default "";

    String args() default "";

    int[] value() default {};
}
