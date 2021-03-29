package com.cimctht.thtzxt.common.constant;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public interface SysConstant {

    /** 项目初始化状态 **/
    String INIT_STATE_OPEN = "open";

    /** 项目初始化状态 **/
    String INIT_STATE_CLOSE = "close";

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

    /** 菜单编码-系统数据 **/
    String MENU_DATA_NAME = "系统数据";

    /** 菜单编码-员工信息 **/
    String MENU_EMPLOYEE_NAME = "员工信息";

    /** 菜单编码-系统参数 **/
    String MENU_PARAMS_NAME = "系统参数";

    /** 菜单编码-密码策略 **/
    String MENU_PASSWORD_NAME = "密码策略";

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

    /** 菜单编码-自定义项 **/
    String MENU_CUSTOM_NAME = "自定义项";

    /** 菜单编码-任务管理 **/
    String MENU_TASK_MANAGER_NAME = "任务管理";

    /** 菜单编码-自定义档案 **/
    String MENU_CUSTOM_FILE_NAME = "自定义档案";

    /** 菜单编码-单据号管理 **/
    String MENU_DOCU_NUM_NAME = "单据号管理";

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

    /** 图标-文件 **/
    String LAYUI_ICON_FILE = "layui-icon-file";

    /** 图标-窗口 **/
    String LAYUI_ICON_LAYER = "layui-icon-layer";

    /** 序列名称定义-员工 **/
    String EMPLOYEE_SEQ = "EMPLOYEE_SEQ";

    /** 序列名称定义-部门 **/
    String DEPART_SEQ = "DEPART_SEQ";

    /** 平台版本参数 **/
    String PARAMS_UNIMAX = "UNIMAX_VERSION";

    /** 单据号最大值 **/
    String DOCUNUM_MAX_VALUE = "999999999999999999";

}
