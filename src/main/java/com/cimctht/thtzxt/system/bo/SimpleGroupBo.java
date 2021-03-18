package com.cimctht.thtzxt.system.bo;

import com.cimctht.thtzxt.system.entity.Group;
import com.cimctht.thtzxt.system.entity.Role;

import java.util.Date;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public class SimpleGroupBo {

    private String code;
    private String name;
    private String description;

    protected String id;
    protected Date createDate;
    protected String createId;
    protected Date modifyDate;
    protected String modifyId;
    protected int isDelete;
    protected String uda1;
    protected String uda2;
    protected String uda3;
    protected String uda4;
    protected String uda5;

    public SimpleGroupBo() {
    }

    public SimpleGroupBo(Group group) {
        this.code = group.getCode();
        this.name = group.getName();
        this.description = group.getDescription();

        this.id = group.getId();
        this.createDate = group.getCreateDate();
        this.createId = group.getCreateId();
        this.modifyDate = group.getModifyDate();
        this.modifyId = group.getModifyId();
        this.isDelete = group.getIsDelete();
        this.uda1 = group.getUda1();
        this.uda2 = group.getUda2();
        this.uda3 = group.getUda3();
        this.uda4 = group.getUda4();
        this.uda5 = group.getUda5();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getUda1() {
        return uda1;
    }

    public void setUda1(String uda1) {
        this.uda1 = uda1;
    }

    public String getUda2() {
        return uda2;
    }

    public void setUda2(String uda2) {
        this.uda2 = uda2;
    }

    public String getUda3() {
        return uda3;
    }

    public void setUda3(String uda3) {
        this.uda3 = uda3;
    }

    public String getUda4() {
        return uda4;
    }

    public void setUda4(String uda4) {
        this.uda4 = uda4;
    }

    public String getUda5() {
        return uda5;
    }

    public void setUda5(String uda5) {
        this.uda5 = uda5;
    }
}
