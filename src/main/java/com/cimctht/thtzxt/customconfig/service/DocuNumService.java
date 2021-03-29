package com.cimctht.thtzxt.customconfig.service;

import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import com.cimctht.thtzxt.common.utils.StringUtils;
import com.cimctht.thtzxt.customconfig.Impl.DocuNumServiceImpl;
import com.cimctht.thtzxt.customconfig.entity.DocuNum;
import com.cimctht.thtzxt.customconfig.repository.DocuNumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@Service
public class DocuNumService implements DocuNumServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(DocuNumService.class);

    @Autowired
    private DocuNumRepository docuNumRepository;

    @Override
    public TableEntity docuNumTableData(String code, String name, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<DocuNum> pages = docuNumRepository.findDocuNumsByIsDeleteAndCodeLikeAndNameLikeOrderByCreateDate(0, StringUtils.string2LikeParam(code),StringUtils.string2LikeParam(name),pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }


}
