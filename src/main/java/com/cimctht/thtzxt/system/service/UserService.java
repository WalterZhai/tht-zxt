package com.cimctht.thtzxt.system.service;

import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import com.cimctht.thtzxt.system.Impl.UserServiceImpl;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.UserRepository;
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
public class UserService implements UserServiceImpl {

    static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TableEntity queryUsersByLikeName(String name, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<User> pages = userRepository.findUsersByIsDeleteAndNameLike(0,"%"+name+"%",pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }
}
