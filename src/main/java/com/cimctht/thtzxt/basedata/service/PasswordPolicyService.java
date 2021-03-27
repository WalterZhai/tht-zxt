package com.cimctht.thtzxt.basedata.service;

import com.cimctht.thtzxt.basedata.Impl.PasswordPolicyServiceImpl;
import com.cimctht.thtzxt.basedata.entity.PasswordPolicy;
import com.cimctht.thtzxt.basedata.repository.PasswordPolicyRepository;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PasswordPolicyService implements PasswordPolicyServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(PasswordPolicyService.class);

    @Autowired
    private PasswordPolicyRepository passwordPolicyRepository;

    @Override
    public TableEntity passwordPolicyTableData(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<PasswordPolicy> pages = passwordPolicyRepository.findPasswordPoliciesByIsDelete(0,pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }



}
