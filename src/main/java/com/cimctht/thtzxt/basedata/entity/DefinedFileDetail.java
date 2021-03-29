package com.cimctht.thtzxt.basedata.entity;


import com.cimctht.thtzxt.common.entity.BaseEntity;

import javax.persistence.*;

/**
 * @comment 自定义档案明细
 * @author Walter(翟笑天)
 * @date 2021/3/27
 */

@Entity
@Table(name="BD_DEFINED_FILE_DETAIL")
public class DefinedFileDetail extends BaseEntity {

    @Column(name = "NAME",columnDefinition = "VARCHAR(30)")
    private String name;
    @Column(name = "CODE",columnDefinition = "VARCHAR(30)")
    private String code;
    @Column(name = "SEQ",columnDefinition = "NUMBER(5)")
    private Integer seq;
    @Column(name = "SEQ",columnDefinition = "NUMBER(1)")
    private Integer isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID",columnDefinition = "VARCHAR(32)")
    private DefinedFile parentDefinedFile;

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public DefinedFile getParentDefinedFile() {
        return parentDefinedFile;
    }

    public void setParentDefinedFile(DefinedFile parentDefinedFile) {
        this.parentDefinedFile = parentDefinedFile;
    }
}
