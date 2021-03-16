package com.cimctht.thtzxt.system.bo;

import com.cimctht.thtzxt.system.entity.User;

import java.util.Date;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public class SimpleUserBo {

    private String loginName;
    private String password;
    private String name;
    private Integer isLocked;
    private String email;
    private String mobile;

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

    public SimpleUserBo() {
    }

    public SimpleUserBo(User user) {
        this.loginName = user.getLoginName();
        this.password = user.getPassword();
        this.name = user.getName();
        this.isLocked = user.getIsLocked();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.id = user.getId();
        this.createDate = user.getCreateDate();
        this.createId = user.getCreateId();
        this.modifyDate = user.getModifyDate();
        this.modifyId = user.getModifyId();
        this.isDelete = user.getIsDelete();
        this.uda1 = user.getUda1();
        this.uda2 = user.getUda2();
        this.uda3 = user.getUda3();
        this.uda4 = user.getUda4();
        this.uda5 = user.getUda5();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Integer isLocked) {
        this.isLocked = isLocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
