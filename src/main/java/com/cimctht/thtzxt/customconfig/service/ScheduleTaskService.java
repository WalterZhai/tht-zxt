package com.cimctht.thtzxt.customconfig.service;

import cn.hutool.core.util.ReflectUtil;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import com.cimctht.thtzxt.common.utils.StringUtils;
import com.cimctht.thtzxt.customconfig.Impl.ScheduleTaskServiceImpl;
import com.cimctht.thtzxt.customconfig.entity.ScheduleTask;
import com.cimctht.thtzxt.customconfig.repository.ScheduleTaskRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@Service
public class ScheduleTaskService implements ScheduleTaskServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(ScheduleTaskService.class);

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public static Map<String,ScheduledFuture> scheduledFutureMap = new HashMap<>();

    @Autowired
    private ScheduleTaskRepository scheduleTaskRepository;

    @Override
    public void init(){
        List<ScheduleTask>  list = scheduleTaskRepository.findScheduleTasksByIsDelete(0);
        for(ScheduleTask scheduleTask : list){
            if(scheduleTask.getIsopen()==0){
                startScheduleTask(scheduleTask);
            }
        }
    }

    @Override
    public void startScheduleTask(ScheduleTask scheduleTask) {
        try{
            ScheduledFuture future = threadPoolTaskScheduler.schedule(new threadTask(scheduleTask.getServiceFullName(),scheduleTask.getServiceName(),scheduleTask.getMethodName()),new CronTrigger(scheduleTask.getCron()));
            scheduledFutureMap.put(scheduleTask.getId(),future);
        }catch (Exception e){
            logger.info(e.getMessage());
        }

    }

    /**
     * @comment 新线程执行
     * @author Walter(翟笑天)
     * @date 2021/3/29
     */
    private class threadTask implements Runnable {
        private String serviceFullName;
        private String serviceName;
        private String methodName;
        threadTask(String serviceFullName,String serviceName,String methodName) {
            this.serviceFullName = serviceFullName;
            this.serviceName = serviceName;
            this.methodName = methodName;
        }
        @SneakyThrows
        @Override
        public void run() {
            Class clz = Class.forName(serviceFullName);
            ReflectUtil.invoke(ReflectUtil.newInstance(clz), methodName);
            logger.info("对象："+serviceFullName+"，方法："+methodName+"执行完成！");
        }
    }

    @Override
    public void stopScheduleTask(ScheduleTask scheduleTask) {
        try{
            ScheduledFuture future = scheduledFutureMap.get(scheduleTask.getId());
            if(future!=null){
                future.cancel(true);
                scheduledFutureMap.remove(scheduleTask.getId());
                logger.info("对象："+scheduleTask.getServiceFullName()+"，方法："+scheduleTask.getMethodName()+"定时任务关闭！");
            }
        }catch (Exception e){
            logger.info(e.getMessage());
        }
    }

    @Override
    public TableEntity scheduleTaskTableData(String name, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<ScheduleTask> pages = scheduleTaskRepository.findScheduleTasksByIsDeleteAndNameLikeOrderByCreateDate(0, StringUtils.string2LikeParam(name),pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public void executeScheduleTask(ScheduleTask scheduleTask){
        try{
            Class clz = Class.forName(scheduleTask.getServiceFullName());
            ReflectUtil.invoke(ReflectUtil.newInstance(clz), scheduleTask.getMethodName());
        }catch (ClassNotFoundException e){
            logger.info("对象："+scheduleTask.getServiceFullName()+"，方法："+scheduleTask.getMethodName()+"立即执行发生错误！"+e.getMessage());
        }
    }

}
