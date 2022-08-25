package com.asl.soatransaction.annotation;

import java.lang.annotation.*;

/**
 * 事务标记注解
 */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SOATransaction {
}
