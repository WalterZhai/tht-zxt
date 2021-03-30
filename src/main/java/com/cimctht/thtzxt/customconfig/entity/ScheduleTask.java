package com.cimctht.thtzxt.customconfig.entity;


import com.cimctht.thtzxt.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @comment 定时任务
 * @author Walter(翟笑天)
 * @date 2021/3/27
 */

@Entity
@Table(name="CCF_SCHEDULE_TASK")
public class ScheduleTask extends BaseEntity {

    @Column(name = "SERVICE_FULL_NAME",columnDefinition = "VARCHAR(400)")
    private String serviceFullName;
    @Column(name = "SERVICE_NAME",columnDefinition = "VARCHAR(200)")
    private String serviceName;
    @Column(name = "METHOD_NAME",columnDefinition = "VARCHAR(200)")
    private String methodName;
    @Column(name = "cron",columnDefinition = "VARCHAR(200)")
    private String cron;
    @Column(name = "IS_OPEN",columnDefinition = "NUMBER(1)")
    private Integer isopen;
    @Column(name = "DESCRIPTION",columnDefinition = "VARCHAR(400)")
    private String description;

    public String getServiceFullName() {
        return serviceFullName;
    }

    public void setServiceFullName(String serviceFullName) {
        this.serviceFullName = serviceFullName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getIsopen() {
        return isopen;
    }

    public void setIsopen(Integer isopen) {
        this.isopen = isopen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
