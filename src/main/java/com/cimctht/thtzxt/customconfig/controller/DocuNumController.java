package com.cimctht.thtzxt.customconfig.controller;

import com.cimctht.thtzxt.basedata.entity.PasswordPolicy;
import com.cimctht.thtzxt.common.constant.SysConstant;
import com.cimctht.thtzxt.common.entity.JsonResult;
import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.customconfig.Impl.DocuNumServiceImpl;
import com.cimctht.thtzxt.customconfig.entity.DocuNum;
import com.cimctht.thtzxt.customconfig.repository.DocuNumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
@RestController
public class DocuNumController {

    static final Logger logger = LoggerFactory.getLogger(DocuNumController.class);

    @Autowired
    private DocuNumServiceImpl docuNumServiceImpl;

    @Autowired
    private DocuNumRepository docuNumRepository;

    @GetMapping(value = "/docuNum/docuNumTableData")
    public TableEntity docuNumTableData(HttpServletRequest request, String code, String name, Integer page, Integer limit) {
        TableEntity table;
        try{
            table = docuNumServiceImpl.docuNumTableData(code, name, page, limit);
        }catch (Exception e){
            table = new TableEntity(e);
        }
        return table;
    }


    @PostMapping(value = "/docuNum/delDocuNum")
    public JsonResult delDocuNum(HttpServletRequest request) {
        String id = request.getParameter("id");
        try{
            DocuNum docuNum = docuNumRepository.findDocuNumById(id);
            docuNum.setIsDelete(1);
            docuNumRepository.save(docuNum);
            return new JsonResult("删除成功");
        }catch (Exception ex){
            return new JsonResult(new UnimaxException("删除失败"));
        }
    }


    @GetMapping(value = "/topage/docuNumEntity")
    public ModelAndView docuNumEntity(HttpServletRequest request) {
        String url = request.getParameter("url");
        url = url.substring(0, url.lastIndexOf("."));
        url = url.replace(".", "/");
        String id = request.getParameter("id");
        DocuNum docuNum = docuNumRepository.findDocuNumById(id);
        ModelAndView modelAndView= new ModelAndView(url).addObject("docuNum",docuNum);
        return modelAndView;
    }

    @PostMapping(value = "/docuNum/addDocuNum")
    public JsonResult addDocuNum(HttpServletRequest request) {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String min = request.getParameter("min");
        String step = request.getParameter("step");
        String description = request.getParameter("description");
        try{
            DocuNum docuNum = new DocuNum();
            docuNum.setCode(code);
            docuNum.setName(name);
            docuNum.setMin(new BigDecimal(min));
            docuNum.setMax(new BigDecimal(SysConstant.DOCUNUM_MAX_VALUE));
            docuNum.setStep(new BigDecimal(step));
            docuNum.setCur(new BigDecimal(min));
            docuNum.setDescription(description);
            docuNumRepository.save(docuNum);
            return new JsonResult("添加成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("添加失败"));
        }
    }

    @PostMapping(value = "/docuNum/editDocuNum")
    public JsonResult editDocuNum(HttpServletRequest request) {
        String id = request.getParameter("id");
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String step = request.getParameter("step");
        String description = request.getParameter("description");
        try{
            DocuNum docuNum = docuNumRepository.findDocuNumById(id);
            docuNum.setCode(code);
            docuNum.setName(name);
            docuNum.setStep(new BigDecimal(step));
            docuNum.setDescription(description);
            docuNumRepository.save(docuNum);
            return new JsonResult("修改成功");
        }catch (Exception e){
            return new JsonResult(new UnimaxException("修改失败"));
        }
    }


}
