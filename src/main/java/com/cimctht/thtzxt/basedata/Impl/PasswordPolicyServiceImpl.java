package com.cimctht.thtzxt.basedata.Impl;

import com.cimctht.thtzxt.basedata.entity.PasswordPolicy;
import com.cimctht.thtzxt.common.entity.TableEntity;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface PasswordPolicyServiceImpl {

    TableEntity passwordPolicyTableData(Integer page, Integer limit);

    void usedPasswordPolicy(PasswordPolicy passwordPolicy);
}
