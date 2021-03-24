package com.cimctht.thtzxt.customconfig.repository;

import com.cimctht.thtzxt.customconfig.entity.Message;
import com.cimctht.thtzxt.customconfig.entity.MessageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface MessageInfoRepository  extends JpaRepository<MessageInfo,String> {

    List<MessageInfo> findMessageInfosByIsDeleteAndMessage(Integer isDelete, Message message);

    List<MessageInfo> findMessageInfosByIsDeleteAndUserCode(Integer isDelete, String userCode);

    Page<MessageInfo> queryMessageInfosByIsDeleteAndIsSendAndUserCodeOrderByIsReadAscCreateDateDesc(Integer isDelete, Integer isSend, String userCode, Pageable pageable);

    MessageInfo findMessageInfoById(String id);

}
