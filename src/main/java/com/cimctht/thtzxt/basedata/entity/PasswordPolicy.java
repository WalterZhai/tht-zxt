package com.cimctht.thtzxt.basedata.entity;


import com.cimctht.thtzxt.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @comment 密码策略
 * @author Walter(翟笑天)
 * @date 2021/3/27
 */
@Entity
@Table(name="BD_PASSWORD_POLICY")
public class PasswordPolicy extends BaseEntity {

    @Column(name = "NAME",columnDefinition = "VARCHAR(30)")
    private String name;
    @Column(name = "VALUE",columnDefinition = "VARCHAR(200)")
    private String value;
    @Column(name = "DESCRIPTION",columnDefinition = "VARCHAR(200)")
    private String description;
    @Column(name = "IS_USED",columnDefinition = "NUMBER(1)")
    private Integer isUsed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }
}
