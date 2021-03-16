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
@Table(name="SYS_ROLE")
public class Role extends BaseEntity {

    @Column(name = "NAME",columnDefinition = "VARCHAR(30)")
    private String name;
    @Column(name = "CODE",columnDefinition = "VARCHAR(30)")
    private String code;
    @Column(name = "DESCRIPTION",columnDefinition = "VARCHAR(200)")
    private String description;
    @ManyToMany(mappedBy = "roles",fetch=FetchType.EAGER)
    private List<User> users = new ArrayList<>();
    @ManyToMany(targetEntity = Menu.class, cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name = "SYS_ROLE_MENU",joinColumns = @JoinColumn(name = "ROLE_ID"),inverseJoinColumns = @JoinColumn(name = "MENU_ID"))
    private List<Menu> menus = new ArrayList<>();


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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
