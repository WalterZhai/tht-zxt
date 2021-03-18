package com.cimctht.thtzxt.basedata.entity;


import com.cimctht.thtzxt.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="BD_EMPLOYEE")
public class Employee extends BaseEntity {

    @Column(name = "CODE",columnDefinition = "VARCHAR(30)")
    private String code;
    @Column(name = "NAME",columnDefinition = "VARCHAR(30)")
    private String name;
    @Column(name = "USED_NAME",columnDefinition = "VARCHAR(30)")
    private String usedName;
    @Column(name = "SEX",columnDefinition = "NUMBER(1)")
    private Integer sex;
    @Column(name = "ID_CARD_NUM",columnDefinition = "VARCHAR(30)")
    private String idCardNum;
    @Column(name = "SOC_CARD_NUM",columnDefinition = "VARCHAR(30)")
    private String socCardNum;
    @Column(name = "BIRTHDAY",columnDefinition = "DATE")
    private Date birthday;
    @Column(name = "ADDRESS",columnDefinition = "VARCHAR(300)")
    private String address;
    @Column(name = "ZIPCODE",columnDefinition = "VARCHAR(30)")
    private String zipcode;
    @Column(name = "OFFICE_TELE",columnDefinition = "VARCHAR(30)")
    private String officeTele;
    @Column(name = "HOME_TELE",columnDefinition = "VARCHAR(30)")
    private String homeTele;
    @Column(name = "MOBILE",columnDefinition = "VARCHAR(30)")
    private String mobile;
    @Column(name = "EMAIL",columnDefinition = "VARCHAR(100)")
    private String email;
    @Column(name = "CAREER_BEGIN_DATE",columnDefinition = "DATE")
    private Date careerBeginDate;
    @Column(name = "JOIN_COMPANY_DATE",columnDefinition = "DATE")
    private Date joinCompanyDate;
    @Column(name = "REMARK",columnDefinition = "VARCHAR(150)")
    private String remark;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPART_ID",columnDefinition = "VARCHAR(32)")
    private Depart depart;

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

    public String getUsedName() {
        return usedName;
    }

    public void setUsedName(String usedName) {
        this.usedName = usedName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getSocCardNum() {
        return socCardNum;
    }

    public void setSocCardNum(String socCardNum) {
        this.socCardNum = socCardNum;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getOfficeTele() {
        return officeTele;
    }

    public void setOfficeTele(String officeTele) {
        this.officeTele = officeTele;
    }

    public String getHomeTele() {
        return homeTele;
    }

    public void setHomeTele(String homeTele) {
        this.homeTele = homeTele;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCareerBeginDate() {
        return careerBeginDate;
    }

    public void setCareerBeginDate(Date careerBeginDate) {
        this.careerBeginDate = careerBeginDate;
    }

    public Date getJoinCompanyDate() {
        return joinCompanyDate;
    }

    public void setJoinCompanyDate(Date joinCompanyDate) {
        this.joinCompanyDate = joinCompanyDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }
}
