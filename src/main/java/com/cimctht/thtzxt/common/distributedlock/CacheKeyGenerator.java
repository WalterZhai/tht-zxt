package com.cimctht.thtzxt.common.distributedlock;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Walter(翟笑天)
 * @Date 2021/7/16
 */
public interface CacheKeyGenerator {
    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}
