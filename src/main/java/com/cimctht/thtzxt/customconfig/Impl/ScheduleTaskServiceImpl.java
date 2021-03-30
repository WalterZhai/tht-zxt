package com.cimctht.thtzxt.customconfig.Impl;

import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.customconfig.entity.ScheduleTask;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface ScheduleTaskServiceImpl {

    /**
     * @comment 开始指定定时任务
     * @author Walter(翟笑天)
     * @date 2021/3/30
     */
    void startScheduleTask(ScheduleTask scheduleTask);

    /**
     * @comment 停止指定定时任务
     * @author Walter(翟笑天)
     * @date 2021/3/30
     */
    void stopScheduleTask(ScheduleTask scheduleTask);

    /**
     * @comment 执行定时任务
     * @author Walter(翟笑天)
     * @date 2021/3/30
     */
    void executeScheduleTask(ScheduleTask scheduleTask) throws ClassNotFoundException;

    /**
     * @comment 启动时初始化定时任务
     * @author Walter(翟笑天)
     * @date 2021/3/30
     */
    void init();


    TableEntity scheduleTaskTableData(String name,Integer page,Integer limit);
}
