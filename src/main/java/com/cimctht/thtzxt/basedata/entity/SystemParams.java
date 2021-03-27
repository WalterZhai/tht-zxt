package com.cimctht.thtzxt.basedata.entity;


import com.cimctht.thtzxt.common.entity.BaseEntity;
import com.cimctht.thtzxt.system.entity.Menu;
import com.cimctht.thtzxt.system.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @comment 系统参数
 * @author Walter(翟笑天)
 * @date 2021/3/27
 */

@Entity
@Table(name="BD_SYSTEM_PARAMS")
public class SystemParams extends BaseEntity {

    @Column(name = "NAME",columnDefinition = "VARCHAR(30)")
    private String name;
    @Column(name = "CODE",columnDefinition = "VARCHAR(30)")
    private String code;
    @Column(name = "VALUE",columnDefinition = "VARCHAR(200)")
    private String value;
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
}
