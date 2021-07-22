package com.cimctht.thtzxt.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cimctht.thtzxt.common.constant.SysConstant;
import com.cimctht.thtzxt.common.distributedlock.CacheLock;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.common.utils.StringUtils;
import com.cimctht.thtzxt.system.Impl.MenuServiceImpl;
import com.cimctht.thtzxt.system.bo.SimpleMenuBo;
import com.cimctht.thtzxt.system.entity.Menu;
import com.cimctht.thtzxt.system.entity.Role;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.MenuRepository;
import com.cimctht.thtzxt.system.repository.RoleRepository;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class MenuController {

    static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuServiceImpl menuServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @PostMapping(value = "/menu/loadSearchInfo")
    public JsonResult loadSearchInfo(HttpServletRequest request, String info) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        try{
            List<String> list = menuServiceImpl.loadSearchInfo(info,username);
            return new JsonResult(list);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/menu/searchAndOpenMenu")
    public JsonResult searchAndOpenMenu(HttpServletRequest request,String info) {
        try{
            String code = info.substring(info.indexOf("(") + 1, info.indexOf(")"));
            Menu menu = menuRepository.findMenuByCodeAndIsDelete(code, 0);
            return new JsonResult(new SimpleMenuBo(menu));
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/menu/ajaxUserLoadTreeChecked")
    public JsonResult ajaxUserLoadTreeChecked(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        try{
            JSONArray array = menuServiceImpl.ajaxUserLoadTreeChecked(username);
            return new JsonResult(array);
        }catch (Exception e){
            return new JsonResult(new UnimaxException("加载树形菜单失败!"));
        }
    }

    @PostMapping(value = "/menu/saveMenuCollect")
    public JsonResult saveMenuCollect(HttpServletRequest request) {
        String treeData = request.getParameter("treeData");
        JSONArray trees = JSON.parseArray(treeData);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        try{
            menuServiceImpl.saveMenuCollect(trees,username);
            return new JsonResult("保存成功!");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/menu/ajaxSelectCollect")
    public JsonResult ajaxSelectCollect(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        try{
            User user = userRepository.findUserByLoginNameAndIsDelete(username,0);
            List<SimpleMenuBo> list = new ArrayList<>();
            for(Menu menu : user.getCollects()){
                list.add(new SimpleMenuBo(menu));
            }
            return new JsonResult(list);
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/menu/ajaxLoadTree")
    public JsonResult ajaxLoadTree(HttpServletRequest request) {
        try{
            JSONArray array = menuServiceImpl.ajaxLoadTree();
            return new JsonResult(array);
        }catch (Exception e){
            return new JsonResult(new UnimaxException("加载树形菜单失败!"));
        }
    }

    @GetMapping(value = "/menu/menuTableData")
    public TableEntity menuTableData(HttpServletRequest request, String id, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = menuServiceImpl.menuTableData(id,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @CacheLock(prefix = "/menu/addMenu")
    @PostMapping(value = "/menu/addMenu")
    public JsonResult addMenu(HttpServletRequest request,String id,String name,String href,Integer type,String icon) {
        try{
            Menu parent = menuRepository.findMenuById(id);
            Menu menu = new Menu();
            menu.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu.setName(name);
            menu.setParentMenu(parent);
            menu.setHref(href);
            menu.setType(type);
            menu.setIcon(icon);
            Integer seq;
            if(parent!=null){
                seq = menuRepository.queryMaxSeqByParentMenuId(parent.getId()) + 1;
            }else{
                seq = menuRepository.queryMaxSeqByParentMenuIsNull() + 1;
            }
            menu.setSeq(seq);
            menuRepository.save(menu);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @CacheLock(prefix = "/menu/editMenu")
    @PostMapping(value = "/menu/editMenu")
    public JsonResult editMenu(HttpServletRequest request,String id,String name,String href,Integer type,String icon) {
        try{
            Menu menu = menuRepository.findMenuById(id);
            menu.setName(name);
            menu.setHref(href);
            menu.setType(type);
            menu.setIcon(icon);
            menuRepository.save(menu);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/menu/delMenu")
    public JsonResult delMenu(HttpServletRequest request,String id) {
        try{
            Menu menu = menuRepository.findMenuById(id);
            if(menu.getChildMenus().size()>0){
                throw new UnimaxException("存在子菜单，无法删除！");
            }

            //清除父菜单
            Menu parent = menu.getParentMenu();
            menu.setParentMenu(null);

            //角色中清除子菜单
            List<Role> roles = menu.getRoles();
            for(Role role : roles){
                if(role.getMenus().contains(menu)){
                    role.getMenus().remove(menu);
                }
            }
            roleRepository.saveAll(roles);
            //用户下清除收藏菜单
            List<User> users = menu.getUsers();
            for(User user : users){
                if(user.getCollects().contains(menu)){
                    user.getCollects().remove(menu);
                }
            }
            userRepository.saveAll(users);

            menu.setIsDelete(1);
            menuRepository.save(menu);

            //所有子菜单重新排序
            menuServiceImpl.sortChildrenSeq(parent);
            return new JsonResult("删除成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/menu/rowUp")
    public JsonResult rowUp(HttpServletRequest request,String id) {
        try{
            menuServiceImpl.rowUp(id);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException("移动失败"));
        }
    }

    @PostMapping(value = "/menu/rowDown")
    public JsonResult rowDown(HttpServletRequest request,String id) {
        try{
            menuServiceImpl.rowDown(id);
            return new JsonResult();
        }catch (Exception e){
            return new JsonResult(new UnimaxException("移动失败"));
        }
    }


}
