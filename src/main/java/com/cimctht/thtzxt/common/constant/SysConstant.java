package com.cimctht.thtzxt.common.constant;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public interface SysConstant {

	/** 默认登录密码 **/
    String PASSWORD = "123456";

    /** admin **/
    String ADMIN = "admin";

    /** 用户组编码前缀 **/
    String SYSTEM_GROUP_CODE_PREFIX = "GROUP_";

    /** 系统管理员角色名称 **/
    String SYSTEM_GROUP_NAME = "系统管理员组";

    /** 系统管理员组描述 **/
    String SYSTEM_GROUP_DESCRIPTION = "信息系统管理员组。";

    /** 角色编码前缀 **/
    String SYSTEM_ROLE_CODE_PREFIX = "ROLE_";

    /** 系统管理员角色名称 **/
    String SYSTEM_ROLE_NAME = "系统管理员";

    /** 系统管理员角色描述 **/
    String SYSTEM_ROLE_DESCRIPTION = "信息系统管理员负责具体信息系统日常管理和维护。";

    /** 菜单编码前缀 **/
    String SYSTEM_MENU_CODE_PREFIX = "MENU_";


    /** 菜单编码-权限管理 **/
    String MENU_PERMIT_MANAGER_NAME = "权限管理";

    /** 菜单编码-用户管理 **/
    String MENU_USER_MANAGER_NAME = "用户管理";

    /** 菜单编码-角色管理 **/
    String MENU_ROLE_MANAGER_NAME = "角色管理";

    /** 菜单编码-菜单管理 **/
    String MENU_MENU_MANAGER_NAME = "菜单管理";

    /** 菜单编码-用户组管理 **/
    String MENU_GROUP_MANAGER_NAME = "用户组管理";

    /** 菜单编码-共享数据 **/
    String MENU_BASE_DATA_NAME = "共享数据";

    /** 菜单编码-组织架构 **/
    String MENU_ORG_NAME = "组织架构";

    /** 菜单编码-员工信息 **/
    String MENU_EMPLOYEE_NAME = "员工信息";

    /** 菜单编码-部门员工 **/
    String MENU_DEPART_EMPLOYEE_NAME = "部门员工";

    /** 菜单编码-部门信息 **/
    String MENU_DEPART_NAME = "部门信息";

    /** 菜单编码-客户化配置 **/
    String MENU_CUSTOM_CONFIG_NAME = "客户化配置";

    /** 菜单编码-消息引擎 **/
    String MENU_MESSAGE_NAME = "消息引擎";

    /** 菜单编码-消息管理 **/
    String MENU_MESSAGE_MANAGER_NAME = "消息管理";

    /** 菜单编码-流程引擎 **/
    String MENU_PROCESS_NAME = "流程引擎";

    /** 菜单编码-流程管理 **/
    String MENU_PROCESS_MANAGER_NAME = "流程管理";

    /** 菜单编码-任务调度 **/
    String MENU_TASK_NAME = "任务调度";

    /** 菜单编码-任务管理 **/
    String MENU_TASK_MANAGER_NAME = "任务管理";

    /** 序列名称定义-角色 **/
    String ROLE_CODE_SEQ = "ROLE_CODE_SEQ";

    /** 序列名称定义-菜单 **/
    String MENU_CODE_SEQ = "MENU_CODE_SEQ";

    /** 序列名称定义-用户组 **/
    String GROUP_CODE_SEQ = "GROUP_CODE_SEQ";

    /** 0 **/
    Integer CONSTANT_ZERO = 0;

    /** 1 **/
    Integer CONSTANT_ONE = 1;

    /** 2 **/
    Integer CONSTANT_TWO = 2;

    /** -1 **/
    Integer CONSTANT_NEGATIVE_ONE = 0;

}
