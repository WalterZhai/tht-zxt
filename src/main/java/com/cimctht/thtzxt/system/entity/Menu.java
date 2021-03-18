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
@Table(name="SYS_MENU")
public class Menu extends BaseEntity {


    @Column(name = "CODE",columnDefinition = "VARCHAR(30)")
    private String code;
    @Column(name = "NAME",columnDefinition = "VARCHAR(30)")
    private String name;
    @Column(name = "HREF",columnDefinition = "VARCHAR(300)")
    private String href;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID",columnDefinition = "VARCHAR(32)")
    private Menu parentMenu;
    @Column(name = "SEQ",columnDefinition = "NUMBER(3)")
    private Integer seq;
    @Column(name = "TYPE",columnDefinition = "NUMBER(1)")
    private Integer type;
    @Column(name = "ICON",columnDefinition = "VARCHAR(30)")
    private String icon;
    @ManyToMany(mappedBy = "menus",fetch=FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "parentMenu", fetch = FetchType.EAGER)
    @OrderBy("seq ASC")
    private List<Menu> childMenus = new ArrayList<>();
    @ManyToMany(mappedBy = "collects",fetch=FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Menu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<Menu> childMenus) {
        this.childMenus = childMenus;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
