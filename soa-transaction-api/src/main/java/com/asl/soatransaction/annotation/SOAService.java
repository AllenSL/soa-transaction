package com.asl.soatransaction.annotation;

import java.lang.annotation.*;

/**
 * 标记分布式事务接口注解
 * @author ansonglin
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SOAService {
}
