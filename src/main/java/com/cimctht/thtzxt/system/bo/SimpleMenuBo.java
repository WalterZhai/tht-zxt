package com.cimctht.thtzxt.system.bo;

import com.cimctht.thtzxt.system.entity.Menu;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public class SimpleMenuBo {

    private String name;
    private String id;
    private String href;
    private String icon;

    public SimpleMenuBo() {
    }

    public SimpleMenuBo(Menu menu) {
        this.name = menu.getName();
        this.id = menu.getId();
        this.href = menu.getHref();
        this.icon = menu.getIcon();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
