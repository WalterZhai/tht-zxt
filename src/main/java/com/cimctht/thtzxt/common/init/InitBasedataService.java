package com.cimctht.thtzxt.common.init;

import cn.hutool.core.util.StrUtil;
import com.cimctht.thtzxt.common.constant.SysConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class InitBasedataService {

    static final Logger logger = LoggerFactory.getLogger(InitBasedataService.class);

    @PersistenceContext(unitName = "unimaxPersistenceUnit")
    private EntityManager unimaxEntityManager;

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


}
