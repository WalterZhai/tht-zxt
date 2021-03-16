package com.cimctht.thtzxt.system.entity;


import com.cimctht.thtzxt.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Entity
@Table(name="SYS_USER")
public class User extends BaseEntity {


    @Column(name = "LOGIN_NAME",columnDefinition = "VARCHAR(30)")
    private String loginName;
    @Column(name = "PASSWORD",columnDefinition = "VARCHAR(30)")
    private String password;
    @Column(name = "NAME",columnDefinition = "VARCHAR(30)")
    private String name;
    @Column(name = "IS_LOCKED",columnDefinition = "number(1) default 0 ")
    private Integer isLocked;
    @Column(name = "EMAIL",columnDefinition = "VARCHAR(30)")
    private String email;
    @Column(name = "MOBILE",columnDefinition = "VARCHAR(20)")
    private String mobile;
    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name = "SYS_USER_ROLE",joinColumns = @JoinColumn(name = "USER_ID"),inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles = new ArrayList<>();
    @ManyToMany(targetEntity = Group.class, cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name = "SYS_USER_GROUP",joinColumns = @JoinColumn(name = "USER_ID"),inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    private List<Group> groups = new ArrayList<>();
    @ManyToMany(targetEntity = Menu.class, cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name = "SYS_USER_MENU",joinColumns = @JoinColumn(name = "USER_ID"),inverseJoinColumns = @JoinColumn(name = "MENU_ID"))
    private List<Menu> collects = new ArrayList<>();

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Menu> getCollects() {
        return collects;
    }

    public void setCollects(List<Menu> collects) {
        this.collects = collects;
    }
}
