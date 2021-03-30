package com.cimctht.thtzxt.common.init;

import cn.hutool.core.util.StrUtil;
import com.cimctht.thtzxt.basedata.entity.PasswordPolicy;
import com.cimctht.thtzxt.basedata.entity.SystemParams;
import com.cimctht.thtzxt.basedata.repository.PasswordPolicyRepository;
import com.cimctht.thtzxt.basedata.repository.SystemParamsRepository;
import com.cimctht.thtzxt.common.constant.SysConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
@Transactional
public class InitBasedataService {

    static final Logger logger = LoggerFactory.getLogger(InitBasedataService.class);

    @PersistenceContext(unitName = "unimaxPersistenceUnit")
    private EntityManager unimaxEntityManager;

    @Autowired
    private SystemParamsRepository systemParamsRepository;

    @Autowired
    private PasswordPolicyRepository passwordPolicyRepository;

    /**
     * @comment 初始化基础表注释
     * @author Walter(翟笑天)
     * @date 2021/3/13
     */
    public void initBaseTableComment(){
        //部门表
        String judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='BD_DEPART'";
        String judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table BD_DEPART is '部门表' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = "comment on column BD_DEPART.code is '部门编码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.name is '部门名称' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.parent_id is '上级部门' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.supervisor_gid is '部门负责人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.commu_address is '通讯地址' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.telephone is '电话' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.fax is '传真' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.remark is '备注' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = "comment on column BD_DEPART.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEPART.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }
        //员工表
        judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='BD_EMPLOYEE'";
        judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table BD_EMPLOYEE is '员工表' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = " comment on column BD_EMPLOYEE.code is '人员编码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.name is '姓名' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.used_name is '曾用名' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.sex is '性别 0:男, 1:女' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.id_card_num is '身份证号' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.soc_card_num is '社会保障号' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.birthday is '出生日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.address is '家庭地址' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.zipcode is '邮政编码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.office_tele is '办公电话' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.home_tele is '家庭电话' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.mobile is '手机号码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.email is '电子邮箱' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.career_begin_date is '参加工作日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.join_company_date is '入职日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.remark is '备注' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.depart_id is '所属部门' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.bank_number is '银行卡号' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.contract_end_date is '合同截止日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.education is '学历' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.job is '职务' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_EMPLOYEE.major is '专业' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = "comment on column BD_EMPLOYEE.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_EMPLOYEE.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_EMPLOYEE.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_EMPLOYEE.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_EMPLOYEE.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_EMPLOYEE.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_EMPLOYEE.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_EMPLOYEE.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_EMPLOYEE.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_EMPLOYEE.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_EMPLOYEE.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }

        //系统参数
        judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='BD_SYSTEM_PARAMS'";
        judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table BD_SYSTEM_PARAMS is '系统参数' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = " comment on column BD_SYSTEM_PARAMS.name is '参数名称' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_SYSTEM_PARAMS.code is '参数编码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_SYSTEM_PARAMS.value is '参数值' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_SYSTEM_PARAMS.description is '参数说明' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = "comment on column BD_SYSTEM_PARAMS.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_SYSTEM_PARAMS.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_SYSTEM_PARAMS.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_SYSTEM_PARAMS.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_SYSTEM_PARAMS.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_SYSTEM_PARAMS.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_SYSTEM_PARAMS.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_SYSTEM_PARAMS.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_SYSTEM_PARAMS.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_SYSTEM_PARAMS.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_SYSTEM_PARAMS.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            //给code创建唯一键
            sql = "ALTER TABLE BD_SYSTEM_PARAMS ADD CONSTRAINT uniq_bd_sp_code UNIQUE(CODE) ENABLE NOVALIDATE ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }

        //密码策略表
        judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='BD_PASSWORD_POLICY'";
        judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table BD_PASSWORD_POLICY is '密码策略' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = " comment on column BD_PASSWORD_POLICY.name is '策略名称' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_PASSWORD_POLICY.value is '正则值' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_PASSWORD_POLICY.IS_USED is '0-正在使用；1-未使用' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_PASSWORD_POLICY.DESCRIPTION is '策略说明' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = "comment on column BD_PASSWORD_POLICY.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_PASSWORD_POLICY.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_PASSWORD_POLICY.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_PASSWORD_POLICY.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_PASSWORD_POLICY.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_PASSWORD_POLICY.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_PASSWORD_POLICY.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_PASSWORD_POLICY.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_PASSWORD_POLICY.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_PASSWORD_POLICY.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_PASSWORD_POLICY.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }

    }


    /**
     * @comment 初始化员工、部门 sequence
     * @author Walter(翟笑天)
     * @date 2021/3/13
     */
    public void initSeq(){
        //员工工号序列
        String judgeSql = "select count(*) from user_sequences where sequence_name= '"+ SysConstant.EMPLOYEE_SEQ+"' ";
        BigDecimal judge = (BigDecimal) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(judge.compareTo(BigDecimal.ZERO)==0){
            String sql = " create sequence "+SysConstant.EMPLOYEE_SEQ+" minvalue 1 maxvalue 9999999999999999999999999999 start with 1 increment by 1 cache 20 ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }
        //部门编码序列
        judgeSql = "select count(*) from user_sequences where sequence_name= '"+SysConstant.DEPART_SEQ+"' ";
        judge = (BigDecimal) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(judge.compareTo(BigDecimal.ZERO)==0){
            String sql = " create sequence "+SysConstant.DEPART_SEQ+" minvalue 1 maxvalue 9999999999999999999999999999 start with 1 increment by 1 cache 20 ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }

    }

    /**
     * @comment 初始化系统参数
     * @author Walter(翟笑天)
     * @date 2021/3/27
     */
    public void initSystemParams(){
        SystemParams unimaxParams = systemParamsRepository.findSystemParamsByIsDeleteAndCode(0,SysConstant.PARAMS_UNIMAX);
        if(unimaxParams==null){
            List<SystemParams> list = new ArrayList<>();
            //平台版本号
            SystemParams systemParams = new SystemParams();
            systemParams.setCode("UNIMAX_VERSION");
            systemParams.setName("平台版本号");
            systemParams.setValue("2.0.0");
            systemParams.setDescription("平台版本号");
            systemParams.setCreateId("admin");
            systemParams.setModifyId("admin");
            list.add(systemParams);
            //数据库版本
            systemParams = new SystemParams();
            systemParams.setCode("DB_VERSION");
            systemParams.setName("数据库版本");
            systemParams.setValue("Oracle 11g");
            systemParams.setDescription("数据库版本");
            systemParams.setCreateId("admin");
            systemParams.setModifyId("admin");
            list.add(systemParams);
            //JAVA版本
            systemParams = new SystemParams();
            systemParams.setCode("JAVA_VERSION");
            systemParams.setName("JAVA版本");
            systemParams.setValue("1.8");
            systemParams.setDescription("JAVA版本");
            systemParams.setCreateId("admin");
            systemParams.setModifyId("admin");
            list.add(systemParams);
            //SPRING BOOT版本号
            systemParams = new SystemParams();
            systemParams.setCode("SPRING_BOOT");
            systemParams.setName("SPRING BOOT版本号");
            systemParams.setValue("2.1.0.RELEASE");
            systemParams.setDescription("SPRING BOOT版本号");
            systemParams.setCreateId("admin");
            systemParams.setModifyId("admin");
            list.add(systemParams);
            systemParamsRepository.saveAll(list);
        }

        List<PasswordPolicy> list = passwordPolicyRepository.findPasswordPoliciesByIsDelete(0);
        if(list==null || list.size()==0){
            PasswordPolicy passwordPolicy = new PasswordPolicy();
            passwordPolicy.setName("简单策略");
            passwordPolicy.setValue("^[a-zA-Z0-9]+$");
            passwordPolicy.setDescription("一个或多个字符或数字。");
            passwordPolicy.setIsUsed(0);
            passwordPolicy.setCreateId("admin");
            passwordPolicy.setModifyId("admin");
            passwordPolicyRepository.save(passwordPolicy);
            passwordPolicy = new PasswordPolicy();
            passwordPolicy.setName("复杂策略");
            passwordPolicy.setValue("^[a-zA-Z]+[0-9]+$");
            passwordPolicy.setDescription("以字符开头，数值结尾。");
            passwordPolicy.setIsUsed(1);
            passwordPolicy.setCreateId("admin");
            passwordPolicy.setModifyId("admin");
            passwordPolicyRepository.save(passwordPolicy);
        }

    }


}
