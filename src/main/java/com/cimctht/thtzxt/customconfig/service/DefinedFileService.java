package com.cimctht.thtzxt.customconfig.service;

import com.cimctht.thtzxt.customconfig.Impl.DefinedFileServiceImpl;
import com.cimctht.thtzxt.customconfig.entity.DefinedFile;
import com.cimctht.thtzxt.customconfig.entity.DefinedFileDetail;
import com.cimctht.thtzxt.customconfig.repository.DefinedFileDetailRepository;
import com.cimctht.thtzxt.customconfig.repository.DefinedFileRepository;
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

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Service
public class DefinedFileService implements DefinedFileServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(DefinedFileService.class);

    @Autowired
    private DefinedFileRepository definedFileRepository;

    @Autowired
    private DefinedFileDetailRepository definedFileDetailRepository;

    @Override
    public TableEntity definedFileTableData(String code, String name, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<DefinedFile> pages = definedFileRepository.findDefinedFilesByIsDeleteAndCodeLikeAndNameLikeOrderByCreateDateAsc(0, StringUtils.string2LikeParam(code),StringUtils.string2LikeParam(name),pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public TableEntity definedFileDetailTableData(String selectid, Integer page, Integer limit) {
        DefinedFile definedFile = definedFileRepository.findDefinedFileById(selectid);
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<DefinedFileDetail> pages = definedFileDetailRepository.findDefinedFileDetailsByIsDeleteAndParentDefinedFileOrderBySeq(0, definedFile,pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

}
