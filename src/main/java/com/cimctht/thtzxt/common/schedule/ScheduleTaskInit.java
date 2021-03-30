package com.cimctht.thtzxt.common.schedule;

import com.cimctht.thtzxt.customconfig.Impl.ScheduleTaskServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Component
@Order(value = 1)
public class ScheduleTaskInit implements ApplicationRunner {

    static final Logger logger = LoggerFactory.getLogger(ScheduleTaskInit.class);

    @Autowired
    private ScheduleTaskServiceImpl scheduleTaskServiceImpl;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler=new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        return threadPoolTaskScheduler;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("定时任务线程池初始化");
        scheduleTaskServiceImpl.init();
        logger.info("定时任务线程池初始化完成");
    }
}
