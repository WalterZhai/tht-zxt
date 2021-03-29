package com.cimctht.thtzxt.customconfig.entity;


import com.cimctht.thtzxt.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @comment 自定义档案
 * @author Walter(翟笑天)
 * @date 2021/3/27
 */

@Entity
@Table(name="CCF_DEFINED_FILE")
public class DefinedFile extends BaseEntity {

    @Column(name = "NAME",columnDefinition = "VARCHAR(30)")
    private String name;
    @Column(name = "CODE",columnDefinition = "VARCHAR(30)")
    private String code;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
