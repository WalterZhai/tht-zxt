package com.cimctht.thtzxt.common.init;

import cn.hutool.core.util.StrUtil;
import com.cimctht.thtzxt.common.constant.SysConstant;
import com.cimctht.thtzxt.common.constant.ThtConstant;
import com.cimctht.thtzxt.common.utils.StringUtils;
import com.cimctht.thtzxt.system.entity.Group;
import com.cimctht.thtzxt.system.entity.Menu;
import com.cimctht.thtzxt.system.entity.Role;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.MenuRepository;
import com.cimctht.thtzxt.system.repository.RoleRepository;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
@Transactional
public class InitImplementsService {

    static final Logger logger = LoggerFactory.getLogger(InitImplementsService.class);

    @PersistenceContext(unitName = "unimaxPersistenceUnit")
    private EntityManager unimaxEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RoleRepository groupRepository;


    /**
     * @comment 初始化spring session 表
     * @author Walter(翟笑天)
     * @date 2021/3/10
     */
    public void initSpringSessionTable(){
        String judgeSql = " select count(*) from user_tables where table_name =upper('SPRING_SESSION') ";
        BigDecimal judge = (BigDecimal) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(judge.compareTo(BigDecimal.ZERO)==0){
            StringBuffer sql = new StringBuffer();
            sql.append("create table SPRING_SESSION( ");
            sql.append("PRIMARY_ID           VARCHAR2(36) not null, ");
            sql.append("SESSION_ID           VARCHAR2(36) not null, ");
            sql.append("CREATION_TIME           number not null, ");
            sql.append("LAST_ACCESS_TIME           number not null, ");
            sql.append("MAX_INACTIVE_INTERVAL           INT not null, ");
            sql.append("EXPIRY_TIME           number not null, ");
            sql.append("PRINCIPAL_NAME           VARCHAR2(100), ");
            sql.append("CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID) ");
            sql.append(")");
            unimaxEntityManager.createNativeQuery(sql.toString()).executeUpdate();
            sql = new StringBuffer();
            sql.append("CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID) ");
            unimaxEntityManager.createNativeQuery(sql.toString()).executeUpdate();
            sql = new StringBuffer();
            sql.append("CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME) ");
            unimaxEntityManager.createNativeQuery(sql.toString()).executeUpdate();
            sql = new StringBuffer();
            sql.append("CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME) ");
            unimaxEntityManager.createNativeQuery(sql.toString()).executeUpdate();
            logger.info("spring session 主表建立成功");
        }
        judgeSql = " select count(*) from user_tables where table_name =upper('SPRING_SESSION_ATTRIBUTES') ";
        judge = (BigDecimal) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(judge.compareTo(BigDecimal.ZERO)==0){
            StringBuffer sql = new StringBuffer();
            sql.append("create table SPRING_SESSION_ATTRIBUTES( ");
            sql.append("SESSION_PRIMARY_ID           VARCHAR2(36) not null, ");
            sql.append("ATTRIBUTE_NAME           VARCHAR2(200) not null, ");
            sql.append("ATTRIBUTE_BYTES           BLOB not null, ");
            sql.append("CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME), ");
            sql.append("CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE ");
            sql.append(") ");
            unimaxEntityManager.createNativeQuery(sql.toString()).executeUpdate();
            sql = new StringBuffer();
            sql.append(" CREATE INDEX SPRING_SESSION_ATTRIBUTES_IX1 ON SPRING_SESSION_ATTRIBUTES (SESSION_PRIMARY_ID) ");
            unimaxEntityManager.createNativeQuery(sql.toString()).executeUpdate();
            logger.info("spring session 副表建立成功");
        }
    }

    /**
     * @comment 初始化基础表注释
     * @author Walter(翟笑天)
     * @date 2021/3/13
     */
    public void initBaseTableComment(){
        //用户表
        String judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='SYS_USER'";
        String judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table SYS_USER is '用户表' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.login_name is '登录名称' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.password is '密码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.name is '用户名' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.is_locked is '锁定状态：0-未锁定；1-已锁定' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.email is '电子邮箱' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.mobile is '手机号码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_USER.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }
        //角色表
        judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='SYS_ROLE'";
        judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table SYS_ROLE is '角色表' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.name is '角色名称' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.code is '角色编码，必须以ROLE_开头' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.description is '角色用途说明' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_ROLE.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }
        //菜单表
        judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='SYS_MENU'";
        judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table SYS_MENU is '菜单表' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.code is '菜单编码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.name is '菜单名称' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.href is '菜单链接' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.PARENT_ID is '父菜单' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.seq is '同父菜单顺序' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.type is '菜单类别：0-web；1-app' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.icon is '菜单图标' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_MENU.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }

        //用户组表
        judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='SYS_GROUP'";
        judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table SYS_GROUP is '用户组表' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.code is '用户组编码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.name is '用户组名称' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.description is '用户组描述' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column SYS_GROUP.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }


    }

    /**
     * @comment 初始化基础sequence
     * @author Walter(翟笑天)
     * @date 2021/3/13
     */
    public void initSeq(){
        //角色编码序列
        String judgeSql = "select count(*) from user_sequences where sequence_name= '"+SysConstant.ROLE_CODE_SEQ+"' ";
        BigDecimal judge = (BigDecimal) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(judge.compareTo(BigDecimal.ZERO)==0){
            String sql = " create sequence "+SysConstant.ROLE_CODE_SEQ+" minvalue 1 maxvalue 9999999999999999999999999999 start with 1 increment by 1 cache 20 ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }
        //菜单编码序列
        judgeSql = "select count(*) from user_sequences where sequence_name= '"+SysConstant.MENU_CODE_SEQ+"' ";
        judge = (BigDecimal) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(judge.compareTo(BigDecimal.ZERO)==0){
            String sql = " create sequence "+SysConstant.MENU_CODE_SEQ+" minvalue 1 maxvalue 9999999999999999999999999999 start with 1 increment by 1 cache 20 ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }
        //菜单编码序列
        judgeSql = "select count(*) from user_sequences where sequence_name= '"+SysConstant.GROUP_CODE_SEQ+"' ";
        judge = (BigDecimal) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(judge.compareTo(BigDecimal.ZERO)==0){
            String sql = " create sequence "+SysConstant.GROUP_CODE_SEQ+" minvalue 1 maxvalue 9999999999999999999999999999 start with 1 increment by 1 cache 20 ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }

    }

    /**
     * @comment 初始化admin用户、系统管理员角色、系统自带菜单
     * @author Walter(翟笑天)
     * @date 2021/3/13
     */
    public void initAdmin(){
        User user = userRepository.findUserByLoginNameAndIsDelete("admin",0);
        if(user==null){
            user = new User();
            user.setLoginName(SysConstant.ADMIN);
            user.setPassword(SysConstant.PASSWORD);
            user.setLoginName(SysConstant.ADMIN);
            user.setEmail("xiaotian.zhai@cimc.com");
            user.setMobile("18260628772");
            user.setIsLocked(0);
            user.setName(SysConstant.ADMIN);
            user.setCreateId(SysConstant.ADMIN);
            user.setModifyId(SysConstant.ADMIN);

            //用户组
            Group group = new Group();
            group.setCode(SysConstant.SYSTEM_GROUP_CODE_PREFIX + StringUtils.padLeft(groupRepository.queryCodeSeqNext().toString(),3,"0"));
            group.setName(SysConstant.SYSTEM_GROUP_NAME);
            group.setDescription(SysConstant.SYSTEM_GROUP_DESCRIPTION);
            group.setCreateId(SysConstant.ADMIN);
            group.setModifyId(SysConstant.ADMIN);
            user.getGroups().add(group);

            //角色
            Role role = new Role();
            role.setCode(SysConstant.SYSTEM_ROLE_CODE_PREFIX + StringUtils.padLeft(roleRepository.queryCodeSeqNext().toString(),3,"0"));
            role.setName(SysConstant.SYSTEM_ROLE_NAME);
            role.setDescription(SysConstant.SYSTEM_ROLE_DESCRIPTION);
            role.setCreateId(SysConstant.ADMIN);
            role.setModifyId(SysConstant.ADMIN);
            user.getRoles().add(role);


            //权限管理 菜单
            Menu menu1 = new Menu();
            menu1.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu1.setName(SysConstant.MENU_PERMIT_MANAGER_NAME);
            menu1.setSeq(1);
            menu1.setType(0);
            menu1.setIcon("layui-icon-auz");
            menu1.setCreateId(SysConstant.ADMIN);
            menu1.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu1);
            //用户管理 菜单
            Menu menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_USER_MANAGER_NAME);
            menu2.setHref("topage?url=system/user/user.html");
            menu2.setParentMenu(menu1);
            menu2.setSeq(1);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //角色管理 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_ROLE_MANAGER_NAME);
            menu2.setHref("topage?url=system/role/role.html");
            menu2.setParentMenu(menu1);
            menu2.setSeq(2);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //菜单管理 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_MENU_MANAGER_NAME);
            menu2.setHref("topage?url=system/menu/menu.html");
            menu2.setParentMenu(menu1);
            menu2.setSeq(3);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //用户组管理 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_GROUP_MANAGER_NAME);
            menu2.setHref("topage?url=system/group/group.html");
            menu2.setParentMenu(menu1);
            menu2.setSeq(4);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);




            //共享数据 菜单
            menu1 = new Menu();
            menu1.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu1.setName(SysConstant.MENU_BASE_DATA_NAME);
            menu1.setSeq(2);
            menu1.setType(0);
            menu1.setIcon("layui-icon-template-1");
            menu1.setCreateId(SysConstant.ADMIN);
            menu1.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu1);
            //系统数据 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_DATA_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(1);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //系统参数 菜单
            Menu menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_PARAMS_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=basedata/system/systemParams.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //密码策略 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_PASSWORD_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=basedata/system/systemPassword.html");
            menu3.setSeq(2);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //组织架构 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_ORG_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(2);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //员工信息 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_EMPLOYEE_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=basedata/employee/employee.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //部门员工 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_DEPART_EMPLOYEE_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=basedata/employee/depart-employee.html");
            menu3.setSeq(2);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //部门信息 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_DEPART_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=basedata/depart/depart.html");
            menu3.setSeq(3);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);

            //客户化配置 菜单
            menu1 = new Menu();
            menu1.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu1.setName(SysConstant.MENU_CUSTOM_CONFIG_NAME);
            menu1.setSeq(3);
            menu1.setType(0);
            menu1.setIcon("layui-icon-set");
            menu1.setCreateId(SysConstant.ADMIN);
            menu1.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu1);
            //消息引擎 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_MESSAGE_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(1);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //消息管理 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_MESSAGE_MANAGER_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=customconfig/message/message.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //流程引擎 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_PROCESS_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(2);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //流程管理 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_PROCESS_MANAGER_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=customconfig/process/process.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //任务调度 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_TASK_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(3);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //任务管理 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_TASK_MANAGER_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=customconfig/task/task.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //自定义项 菜单
            menu2 = new Menu();
            menu2.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu2.setName(SysConstant.MENU_CUSTOM_NAME);
            menu2.setParentMenu(menu1);
            menu2.setSeq(4);
            menu2.setType(0);
            menu2.setIcon(SysConstant.LAYUI_ICON_FILE);
            menu2.setCreateId(SysConstant.ADMIN);
            menu2.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu2);
            menu1.getChildMenus().add(menu2);
            //自定义档案 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_CUSTOM_FILE_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=customconfig/custom/customFile.html");
            menu3.setSeq(1);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);
            //单据号管理 菜单
            menu3 = new Menu();
            menu3.setCode(SysConstant.SYSTEM_MENU_CODE_PREFIX + StringUtils.padLeft(menuRepository.queryCodeSeqNext().toString(),3,"0"));
            menu3.setName(SysConstant.MENU_DOCU_NUM_NAME);
            menu3.setParentMenu(menu2);
            menu3.setHref("topage?url=customconfig/custom/doucNum.html");
            menu3.setSeq(2);
            menu3.setType(0);
            menu3.setIcon(SysConstant.LAYUI_ICON_LAYER);
            menu3.setCreateId(SysConstant.ADMIN);
            menu3.setModifyId(SysConstant.ADMIN);
            role.getMenus().add(menu3);
            menu2.getChildMenus().add(menu3);

            userRepository.save(user);
        }
    }


}
