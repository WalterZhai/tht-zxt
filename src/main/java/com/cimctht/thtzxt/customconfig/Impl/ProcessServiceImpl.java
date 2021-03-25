package com.cimctht.thtzxt.customconfig.Impl;


import com.cimctht.thtzxt.common.entity.TableEntity;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/25
 */
public interface ProcessServiceImpl {

    /**
     * @comment 表格查询
     * @author Walter(翟笑天)
     * @date 2021/3/25
     */
    TableEntity findModels(String title, Integer page, Integer limit);

    /**
     * @comment 根据流程定义key，创建实例流程，并返回实例流程id
     * @author Walter(翟笑天)
     * @date 2021/3/25
     */
    String startProcessByKey(String processDefinitionKey);

    /**
     * @comment 根据实例id查询当前节点集合
     * @author Walter(翟笑天)
     * @date 2021/3/25
     */
    List<Task> getTask(String processInstanceId);

    /**
     * @comment 根据个人userId，查询个人需要执行的任务
     * @author Walter(翟笑天)
     * @date 2021/3/25
     */
    List<Task> getTaskByUserId(String userId);

    /**
     * @comment 根据个人实例id和userId，查询个人在当前实例需要执行的任务
     * @author Walter(翟笑天)
     * @date 2021/3/25
     */
    List<Task> getTaskByProcessInstanceIdAndUserId(String processInstanceId, String userId);

    /**
     * @comment 根据任务id，完成任务
     * @author Walter(翟笑天)
     * @date 2021/3/25
     */
    void completeTask(String taskId);

    /**
     * @comment 根据实例id，判断是否已走完流程
     * @author Walter(翟笑天)
     * @date 2021/3/25
     */
    boolean isEnd(String processInstanceId);

}
