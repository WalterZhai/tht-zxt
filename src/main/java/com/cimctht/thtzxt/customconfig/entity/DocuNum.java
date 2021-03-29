package com.cimctht.thtzxt.customconfig.entity;


import com.cimctht.thtzxt.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

/**
 * @comment 单据号管理
 * @author Walter(翟笑天)
 * @date 2021/3/27
 */

@Entity
@Table(name="CCF_DOCU_NUM")
public class DocuNum extends BaseEntity {

    @Column(name = "NAME",columnDefinition = "VARCHAR(30)")
    private String name;
    @Column(name = "CODE", columnDefinition = "VARCHAR(30)")
    private String code;
    @Column(name = "MIN",columnDefinition = "NUMBER(20)")
    private BigDecimal min;
    @Column(name = "MAX",columnDefinition = "NUMBER(20)")
    private BigDecimal max;
    @Column(name = "STEP",columnDefinition = "NUMBER(5)")
    private BigDecimal step;
    @Column(name = "CUR",columnDefinition = "NUMBER(20)")
    private BigDecimal cur;
    @Column(name = "DESCRIPTION",columnDefinition = "VARCHAR(200)")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getStep() {
        return step;
    }

    public void setStep(BigDecimal step) {
        this.step = step;
    }

    public BigDecimal getCur() {
        return cur;
    }

    public void setCur(BigDecimal cur) {
        this.cur = cur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
