package com.asl.soatransaction.annocation;

import java.lang.annotation.*;

/**
 * @author ansonglin
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SOAService {
}
