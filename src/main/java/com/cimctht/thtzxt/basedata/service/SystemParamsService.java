package com.cimctht.thtzxt.basedata.service;

import com.cimctht.thtzxt.basedata.Impl.SystemParamsServiceImpl;
import com.cimctht.thtzxt.basedata.bo.SimpleEmployeeBo;
import com.cimctht.thtzxt.basedata.entity.Employee;
import com.cimctht.thtzxt.basedata.entity.SystemParams;
import com.cimctht.thtzxt.basedata.repository.SystemParamsRepository;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import com.cimctht.thtzxt.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@Service
public class SystemParamsService implements SystemParamsServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(SystemParamsService.class);

    @Autowired
    private SystemParamsRepository systemParamsRepository;

    @Override
    public TableEntity systemParamsTableData(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<SystemParams> pages = systemParamsRepository.findSystemParamsByIsDelete(0,pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }



}
