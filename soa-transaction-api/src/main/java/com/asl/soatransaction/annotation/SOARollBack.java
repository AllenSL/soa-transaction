package com.asl.soatransaction.annotation;

import java.lang.annotation.*;

/**
 * 回滚事务注解
 * @author ansonglin
 */
@Target({ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SOARollBack {

}
