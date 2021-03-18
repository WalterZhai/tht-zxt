package com.cimctht.thtzxt.basedata.entity;


import com.cimctht.thtzxt.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Entity
@Table(name="BD_DEPART")
public class Depart extends BaseEntity {

    @Column(name = "CODE",columnDefinition = "VARCHAR(30)")
    private String code;
    @Column(name = "NAME",columnDefinition = "VARCHAR(200)")
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID",columnDefinition = "VARCHAR(32)")
    private Depart parentDepart;
    @OneToMany(mappedBy = "parentDepart", fetch = FetchType.EAGER)
    @OrderBy("code ASC")
    private List<Depart> childDeparts = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPERVISOR_GID")
    private Employee supervisor;
    @Column(name = "COMMU_ADDRESS")
    private String commuAddress;
    @Column(name = "TELEPHONE")
    private String telephone;
    @Column(name = "FAX")
    private String fax;
    @Column(name = "REMARK")
    private String remark;

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

    public Depart getParentDepart() {
        return parentDepart;
    }

    public void setParentDepart(Depart parentDepart) {
        this.parentDepart = parentDepart;
    }

    public List<Depart> getChildDeparts() {
        return childDeparts;
    }

    public void setChildDeparts(List<Depart> childDeparts) {
        this.childDeparts = childDeparts;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
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

}
