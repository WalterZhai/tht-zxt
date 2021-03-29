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

    @Column(name = "SERVICE_NAME",columnDefinition = "VARCHAR(200)")
    private String serviceName;
    @Column(name = "METHOD_NAME",columnDefinition = "VARCHAR(200)")
    private String methodName;
    @Column(name = "CORN",columnDefinition = "VARCHAR(200)")
    private String corn;
    @Column(name = "IS_OPEN",columnDefinition = "NUMBER(1)")
    private Integer isopen;
    @Column(name = "DESCRIPTION",columnDefinition = "VARCHAR(400)")
    private String description;

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

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
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
