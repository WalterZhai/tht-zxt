package com.cimctht.thtzxt.customconfig.entity;

import com.cimctht.thtzxt.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@Entity
@Table(name="CCF_MESSAGE")
public class Message extends BaseEntity {

    @Column(name = "CODE",columnDefinition = "VARCHAR(30)")
    private String code;
    @Column(name = "NAME",columnDefinition = "VARCHAR(30)")
    private String name;
    @Column(name = "TITLE",columnDefinition = "VARCHAR(100)")
    private String title;
    @Column(name = "CONTENT",columnDefinition = "VARCHAR(1000)")
    private String content;
    @Column(name = "IS_TYPE",columnDefinition = "number(1)")
    private Integer isType;
    @Column(name = "IS_SEND",columnDefinition = "number(1)")
    private Integer isSend;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsType() {
        return isType;
    }

    public void setIsType(Integer isType) {
        this.isType = isType;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }
}
