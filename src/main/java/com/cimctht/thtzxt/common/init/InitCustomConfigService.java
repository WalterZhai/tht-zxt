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
public class InitCustomConfigService {

    static final Logger logger = LoggerFactory.getLogger(InitCustomConfigService.class);

    @PersistenceContext(unitName = "unimaxPersistenceUnit")
    private EntityManager unimaxEntityManager;


    /**
     * @comment 初始化客户化配置表注释
     * @author Walter(翟笑天)
     * @date 2021/3/13
     */
    public void InitCustomConfigComment(){
        //信息主表
        String judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='CCF_MESSAGE'";
        String judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table CCF_MESSAGE is '站内信息主表' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = "comment on column CCF_MESSAGE.code is '发送人编码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.name is '发送人名称' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.title is '标题' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.content is '内容' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.is_type is '发送类型 0-所有用户；1-角色；2-指定用户；3-用户组' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.is_send is '是否已发送 0-未发送；1-已发送' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = "comment on column CCF_MESSAGE.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }
        //信息副表
        judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='CCF_MESSAGE_INFO'";
        judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table CCF_MESSAGE_INFO is '站内信息副表' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = " comment on column CCF_MESSAGE_INFO.user_code is '用户编码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column CCF_MESSAGE_INFO.is_read is '是否已读 0-未读；1-已读' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column CCF_MESSAGE_INFO.is_send is '是否已发送 0-未发送；1-已发送' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = "comment on column CCF_MESSAGE_INFO.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE_INFO.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE_INFO.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE_INFO.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE_INFO.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE_INFO.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE_INFO.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE_INFO.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE_INFO.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE_INFO.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column CCF_MESSAGE_INFO.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }

        //自定义档案主表
        judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='BD_DEFINED_FILE'";
        judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table BD_DEFINED_FILE is '自定义档案主表' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = " comment on column BD_DEFINED_FILE.name is '档案名称' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_DEFINED_FILE.code is '档案编码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_DEFINED_FILE.DESCRIPTION is '档案说明' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = "comment on column BD_DEFINED_FILE.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }

        //自定义档案副表
        judgeSql = "select c.comments from user_tab_comments c where c.TABLE_NAME='BD_DEFINED_FILE_DETAIL'";
        judge = (String) unimaxEntityManager.createNativeQuery(judgeSql).getSingleResult();
        if(StrUtil.hasEmpty(judge)){
            String sql = "comment on table BD_DEFINED_FILE_DETAIL is '自定义档案明细' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = " comment on column BD_DEFINED_FILE_DETAIL.name is '名称' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_DEFINED_FILE_DETAIL.code is '编码' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_DEFINED_FILE_DETAIL.seq is '顺序' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_DEFINED_FILE_DETAIL.IS_ACTIVE is '是否激活：0-激活；1-未激活' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_DEFINED_FILE_DETAIL.value is '档案值' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = " comment on column BD_DEFINED_FILE_DETAIL.remark is '备注' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();

            sql = "comment on column BD_DEFINED_FILE_DETAIL.gid is '主键' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE_DETAIL.create_date is '创建时间' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE_DETAIL.create_id is '创建人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE_DETAIL.modify_date is '修改日期' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE_DETAIL.modify_id is '修改人' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE_DETAIL.is_delete is '删除标识：0-未删除；1-删除' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE_DETAIL.uda1 is '备用1' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE_DETAIL.uda2 is '备用2' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE_DETAIL.uda3 is '备用3' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE_DETAIL.uda4 is '备用4' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
            sql = "comment on column BD_DEFINED_FILE_DETAIL.uda5 is '备用5' ";
            unimaxEntityManager.createNativeQuery(sql).executeUpdate();
        }

    }



}
