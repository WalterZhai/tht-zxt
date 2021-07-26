package com.cimctht.thtzxt.common.distributedlock;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Walter(翟笑天)
 * @Date 2021/7/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {
    /**
     * 锁key的前缀
     *
     * @return redis 锁key的前缀
     */
    String prefix() default "";

    /**
     * 过期秒数,默认为10秒
     *
     * @return 轮询锁的时间
     */
    int expire() default 10;

    /**
     * 超时时间单位
     *
     * @return 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * <p>Key的分隔符（默认 :）</p>
     *
     * @return String
     */
    String delimiter() default ":";

    /**
     * <p>类型：默认个人模式 personal 会在key中加session id
     * 集群模式 : cluster 不会在key中加session id 适用于分布式下锁住url
     * </p>
     *
     * @return String
     */
    String type() default "personal";
}
