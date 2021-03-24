package com.cimctht.thtzxt.customconfig.repository;

import com.cimctht.thtzxt.customconfig.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/19
 */
public interface MessageRepository extends JpaRepository<Message,String> {

    Message findMessageById(String id);

    Page<Message> findMessagesByIsDeleteAndTitleLikeAndIsTypeAndIsSendOrderByCreateDateDesc(Integer isDelete, String title, Integer type, Integer isSend, Pageable pageable);

    Page<Message> findMessagesByIsDeleteAndTitleLikeAndIsTypeOrderByCreateDateDesc(Integer isDelete, String title, Integer type, Pageable pageable);

    Page<Message> findMessagesByIsDeleteAndTitleLikeAndIsSendOrderByCreateDateDesc(Integer isDelete, String title, Integer isSend, Pageable pageable);

    Page<Message> findMessagesByIsDeleteAndTitleLikeOrderByCreateDateDesc(Integer isDelete, String title, Pageable pageable);
}
