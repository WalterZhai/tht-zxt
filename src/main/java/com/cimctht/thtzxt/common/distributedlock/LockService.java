package com.cimctht.thtzxt.common.distributedlock;

import com.cimctht.thtzxt.common.bean.ManageSpringBeans;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.common.utils.TimeUtils;
import com.cimctht.thtzxt.system.entity.DistributedLock;
import com.cimctht.thtzxt.system.repository.DistributedLockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
public class LockService {

    static final Logger logger = LoggerFactory.getLogger(LockService.class);

    /**
     * @comment 定时任务删除分布式锁key数据
     * @author Walter(翟笑天)
     * @date 2021/7/13
     */
    public void distributedLockVerify() {
        //因为分布式锁默认timeout是5秒钟
        //找到timeout已经超过5秒的，所有锁，再对比时间和单位，看是否超时，超时则删除，其他不管
        try{
            DistributedLockRepository distributedLockRepository = ManageSpringBeans.getBean("distributedLockRepository");
            long l = System.currentTimeMillis() - 5000l;
            List<DistributedLock> list =  distributedLockRepository.findDistributedLocksByCreateDateLessThan(new Date(l));
            Integer ct = 0;
            for(DistributedLock distributedLock : list){
                Date outTime = convTime(distributedLock);
                if(new Date().after(outTime)){
                    distributedLockRepository.delete(distributedLock);
                    ct += 1;
                }
            }
            if (ct > 0) {
                logger.info("定时任务更新分布式锁，删除" + ct + "条锁数据。");
            }
        }catch (Exception e){
            logger.info("定时任务更新分布式锁触发错误：" + e.getMessage());
        }
    }

    public Date convTime(DistributedLock distributedLock) {
        Long l = distributedLock.getCreateDate().getTime();
        if (distributedLock.getTimeUnit().equals(TimeUnit.MICROSECONDS.toString())) {
            l = l + TimeUnit.MICROSECONDS.toMillis(distributedLock.getExpire());
        } else if (distributedLock.getTimeUnit().equals(TimeUnit.DAYS.toString())) {
            l = l + TimeUnit.DAYS.toMillis(distributedLock.getExpire());
        } else if (distributedLock.getTimeUnit().equals(TimeUnit.NANOSECONDS.toString())) {
            l = l + TimeUnit.NANOSECONDS.toMillis(distributedLock.getExpire());
        } else if (distributedLock.getTimeUnit().equals(TimeUnit.SECONDS.toString())) {
            l = l + TimeUnit.SECONDS.toMillis(distributedLock.getExpire());
        } else if (distributedLock.getTimeUnit().equals(TimeUnit.MINUTES.toString())) {
            l = l + TimeUnit.MINUTES.toMillis(distributedLock.getExpire());
        } else if (distributedLock.getTimeUnit().equals(TimeUnit.HOURS.toString())) {
            l = l + TimeUnit.HOURS.toMillis(distributedLock.getExpire());
        } else {
            l = Long.parseLong(distributedLock.getExpire().toString());
        }
        return new Date(l);
    }


}
