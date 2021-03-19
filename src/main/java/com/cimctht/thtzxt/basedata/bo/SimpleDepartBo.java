package com.cimctht.thtzxt.basedata.bo;

import com.cimctht.thtzxt.basedata.entity.Depart;

import java.util.Date;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public class SimpleDepartBo {

    private String code;
    private String name;
    private String parentDepartName;
    private String supervisorId;
    private String supervisorName;
    private String commuAddress;
    private String telephone;
    private String fax;
    private String remark;

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

    public SimpleDepartBo() {
    }

    public SimpleDepartBo(Depart depart) {
        this.code = depart.getCode();
        this.name = depart.getName();
        this.parentDepartName = depart.getParentDepart()==null?"":depart.getParentDepart().getName();
        this.supervisorId = depart.getSupervisor()==null?"":depart.getSupervisor().getId();
        this.supervisorName = depart.getSupervisor()==null?"":depart.getSupervisor().getName();
        this.commuAddress = depart.getCommuAddress();
        this.telephone = depart.getTelephone();
        this.fax = depart.getFax();
        this.remark = depart.getRemark();

        this.id = depart.getId();
        this.createDate = depart.getCreateDate();
        this.createId = depart.getCreateId();
        this.modifyDate = depart.getModifyDate();
        this.modifyId = depart.getModifyId();
        this.isDelete = depart.getIsDelete();
        this.uda1 = depart.getUda1();
        this.uda2 = depart.getUda2();
        this.uda3 = depart.getUda3();
        this.uda4 = depart.getUda4();
        this.uda5 = depart.getUda5();
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

    public String getParentDepartName() {
        return parentDepartName;
    }

    public void setParentDepartName(String parentDepartName) {
        this.parentDepartName = parentDepartName;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getCommuAddress() {
        return commuAddress;
    }

    public void setCommuAddress(String commuAddress) {
        this.commuAddress = commuAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }
}
