package com.cimctht.thtzxt.common.distributedlock;

import java.lang.annotation.*;

/**
 * @author Walter(翟笑天)
 * @Date 2021/7/16
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {
    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}
