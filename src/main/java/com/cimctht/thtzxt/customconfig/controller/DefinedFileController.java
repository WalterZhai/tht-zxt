package com.cimctht.thtzxt.customconfig.controller;

import com.alibaba.fastjson.JSON;
import com.cimctht.thtzxt.common.distributedlock.CacheLock;
import com.cimctht.thtzxt.customconfig.Impl.DefinedFileServiceImpl;
import com.cimctht.thtzxt.customconfig.entity.DefinedFile;
import com.cimctht.thtzxt.customconfig.entity.DefinedFileDetail;
import com.cimctht.thtzxt.customconfig.repository.DefinedFileDetailRepository;
import com.cimctht.thtzxt.customconfig.repository.DefinedFileRepository;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@RestController
public class DefinedFileController {

    static final Logger logger = LoggerFactory.getLogger(DefinedFileController.class);

    @Autowired
    private DefinedFileServiceImpl definedFileServiceImpl;

    @Autowired
    private DefinedFileRepository definedFileRepository;

    @Autowired
    private DefinedFileDetailRepository definedFileDetailRepository;


    @GetMapping(value = "/definedFile/definedFileTableData")
    public TableEntity definedFileTableData(HttpServletRequest request, String code, String name, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = definedFileServiceImpl.definedFileTableData(code,name,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @GetMapping(value = "/definedFileDetail/definedFileDetailTableData")
    public TableEntity definedFileDetailTableData(HttpServletRequest request, String selectid, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = definedFileServiceImpl.definedFileDetailTableData(selectid,page,limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }

    @CacheLock(prefix = "/definedFile/addDefinedFile")
    @PostMapping(value = "/definedFile/addDefinedFile")
    public JsonResult addDefinedFile(HttpServletRequest request, String code, String name, String description) {
        try{
            DefinedFile definedFile = new DefinedFile();
            definedFile.setCode(code);
            definedFile.setName(name);
            definedFile.setDescription(description);
            definedFileRepository.save(definedFile);
            return new JsonResult("生成成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @CacheLock(prefix = "/definedFile/editDefinedFile")
    @PostMapping(value = "/definedFile/editDefinedFile")
    public JsonResult editDefinedFile(HttpServletRequest request,String id,String code,String name,String description) {
        try{
            DefinedFile definedFile = definedFileRepository.findDefinedFileById(id);
            definedFile.setCode(code);
            definedFile.setName(name);
            definedFile.setDescription(description);
            definedFileRepository.save(definedFile);
            return new JsonResult("编辑成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/definedFile/delDefinedFile")
    public JsonResult delDefinedFile(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            DefinedFile definedFile = definedFileRepository.findDefinedFileById(id);
            List<DefinedFileDetail> list = definedFileDetailRepository.findDefinedFileDetailsByIsDeleteAndParentDefinedFile(0,definedFile);
           for(DefinedFileDetail definedFileDetail : list){
               definedFileDetail.setIsDelete(1);
           }
            definedFile.setIsDelete(1);
            definedFileDetailRepository.saveAll(list);
            definedFileRepository.save(definedFile);
            return new JsonResult("删除成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException(ex.getMessage()));
        }
    }


    @CacheLock(prefix = "/definedFileDetail/addDefinedFileDetail")
    @PostMapping(value = "/definedFileDetail/addDefinedFileDetail")
    public JsonResult addDefinedFileDetail(HttpServletRequest request, String definedid, String code, String name, Integer seq, String value, String remark) {
        try{
            DefinedFile definedFile = definedFileRepository.findDefinedFileById(definedid);
            DefinedFileDetail definedFileDetail = new DefinedFileDetail();
            definedFileDetail.setParentDefinedFile(definedFile);
            definedFileDetail.setCode(code);
            definedFileDetail.setName(name);
            definedFileDetail.setSeq(seq);
            definedFileDetail.setValue(value);
            definedFileDetail.setRemark(remark);
            definedFileDetail.setIsActive(0);
            definedFileDetailRepository.save(definedFileDetail);
            return new JsonResult("生成成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }

    @PostMapping(value = "/definedFileDetail/delDefinedFileDetail")
    public JsonResult delDefinedFileDetail(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            DefinedFileDetail definedFileDetail = definedFileDetailRepository.findDefinedFileDetailById(id);
            definedFileDetail.setIsDelete(1);
            definedFileDetailRepository.save(definedFileDetail);
            return new JsonResult("删除成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException(ex.getMessage()));
        }
    }

    @CacheLock(prefix = "/definedFileDetail/editDefinedFileDetail")
    @PostMapping(value = "/definedFileDetail/editDefinedFileDetail")
    public JsonResult editDefinedFileDetail(HttpServletRequest request, String id, String code, String name, Integer seq, String value, String remark) {
        try{
            DefinedFileDetail definedFileDetail = definedFileDetailRepository.findDefinedFileDetailById(id);
            definedFileDetail.setCode(code);
            definedFileDetail.setName(name);
            definedFileDetail.setSeq(seq);
            definedFileDetail.setValue(value);
            definedFileDetail.setRemark(remark);
            definedFileDetailRepository.save(definedFileDetail);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException(e.getMessage()));
        }
    }


    @PostMapping(value = "/definedFileDetail/activeDefinedFileDetail")
    public JsonResult activeDefinedFileDetail(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<DefinedFileDetail> list = JSON.parseArray(arrs,DefinedFileDetail.class);
        try{
            for(DefinedFileDetail definedFileDetail : list){
                definedFileDetail.setIsActive(0);
            }
            definedFileDetailRepository.saveAll(list);
            return new JsonResult("激活成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("激活失败"));
        }
    }

    @PostMapping(value = "/definedFileDetail/noActiveDefinedFileDetail")
    public JsonResult noActiveDefinedFileDetail(HttpServletRequest request) {
        String arrs = request.getParameter("arrs");
        List<DefinedFileDetail> list = JSON.parseArray(arrs,DefinedFileDetail.class);
        try{
            for(DefinedFileDetail definedFileDetail : list){
                definedFileDetail.setIsActive(1);
            }
            definedFileDetailRepository.saveAll(list);
            return new JsonResult("取消激活成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("取消激活失败"));
        }
    }

}
