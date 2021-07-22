package com.cimctht.thtzxt.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Walter(翟笑天)
 * @Date 2021/7/21
 */
@Entity
@Table(name="SYS_DISTRIBUTED_LOCK")
public class DistributedLock {
    @Id
    @Column(name = "LOCK_KEY",columnDefinition = "VARCHAR(300)")//锁key
    private String lockKey;
    @Column(name = "CREATE_DATE",updatable = false,nullable = false,columnDefinition = "DATE DEFAULT SYSDATE ")//创建时间
    protected Date createDate;
    @Column(name = "expire",columnDefinition = "int")//过期时间值
    private Integer expire;
    @Column(name = "TIME_UNIT",columnDefinition = "VARCHAR(30)")//过期时间单位
    private String timeUnit;

    public DistributedLock() {
        super();
    }

    public DistributedLock(String lockKey, Date createDate, Integer expire, String timeUnit) {
        this.lockKey = lockKey;
        this.createDate = createDate;
        this.expire = expire;
        this.timeUnit = timeUnit;
    }

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }
}
