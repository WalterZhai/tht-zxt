package com.cimctht.thtzxt.basedata.Impl;

import com.cimctht.thtzxt.basedata.entity.PasswordPolicy;
import com.cimctht.thtzxt.common.entity.TableEntity;

public interface PasswordPolicyServiceImpl {

    TableEntity passwordPolicyTableData(Integer page, Integer limit);

    void usedPasswordPolicy(PasswordPolicy passwordPolicy);
}
