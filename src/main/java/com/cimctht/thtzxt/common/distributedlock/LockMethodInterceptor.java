package com.cimctht.thtzxt.common.distributedlock;

import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.system.entity.DistributedLock;
import com.cimctht.thtzxt.system.repository.DistributedLockRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author Walter(翟笑天)
 * @Date 2021/7/16
 */
@Aspect
@Configuration
public class LockMethodInterceptor {

    @Autowired
    public LockMethodInterceptor(DistributedLockRepository distributedLockRepository, CacheKeyGenerator cacheKeyGenerator,LockController lockController ) {
        this.distributedLockRepository = distributedLockRepository;
        this.cacheKeyGenerator = cacheKeyGenerator;
        this.lockController = lockController;
    }

    private final DistributedLockRepository distributedLockRepository;
    private final CacheKeyGenerator cacheKeyGenerator;
    private final LockController lockController;


    @Around("execution(public * *(..)) && @annotation(com.cimctht.thtzxt.common.distributedlock.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            throw new RuntimeException("lock key don't null...");
        }
        final String lockKey = cacheKeyGenerator.getLockKey(pjp);
        final DistributedLock distributedLock = distributedLockRepository.findDistributedLockByLockKey(lockKey);
        if (distributedLock != null) {
            return lockController.lockExceptionControllerMothed("请勿重复请求");
        }else{
            DistributedLock disLock = new DistributedLock(lockKey, new Date(), lock.expire(), lock.timeUnit().toString());
            distributedLockRepository.save(disLock);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            return lockController.lockExceptionControllerMothed(throwable.getMessage());
        } finally {
            //方法运行完后删除lockKey值，释放请求
            distributedLockRepository.deleteDistributedLockByLockKey(lockKey);
        }
    }
}
