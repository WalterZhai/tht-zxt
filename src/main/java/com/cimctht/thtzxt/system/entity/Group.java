package com.cimctht.thtzxt.system.entity;


import com.cimctht.thtzxt.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Entity
@Table(name="SYS_GROUP")
public class Group extends BaseEntity {

    @Column(name = "CODE",columnDefinition = "VARCHAR(30)")
    private String code;
    @Column(name = "NAME",columnDefinition = "VARCHAR(30)")
    private String name;
    @Column(name = "DESCRIPTION",columnDefinition = "VARCHAR(200)")
    private String description;
    @ManyToMany(mappedBy = "groups")
    private List<User> users = new ArrayList<>();

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
