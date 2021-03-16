package com.cimctht.thtzxt.system.service;

import com.cimctht.thtzxt.common.entity.TableEntity;
import com.cimctht.thtzxt.common.exception.UnimaxException;
import com.cimctht.thtzxt.common.utils.MathsUtils;
import com.cimctht.thtzxt.system.Impl.UserServiceImpl;
import com.cimctht.thtzxt.system.entity.User;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

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

    @PersistenceContext(unitName = "unimaxPersistenceUnit")
    private EntityManager unimaxEntityManager;

    @Override
    public TableEntity queryUsersByLikeName(String name, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<User> pages = userRepository.findUsersByIsDeleteAndNameLike(0,"%"+name+"%",pageable);
        return new TableEntity(pages.getContent(), MathsUtils.convertLong2BigDecimal(pages.getTotalElements()));
    }

    @Override
    public TableEntity onlineUserData(Integer page, Integer limit) {
        String sql = "select u.name,u.login_name as loginName,t.creation_time as createTime,t.last_access_time as lastAccessTime from SPRING_SESSION t left join SYS_USER u on t.principal_name=u.login_name";
        Query query = unimaxEntityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> resultList = query.getResultList();
        return new TableEntity(resultList, MathsUtils.convertInteger2BigDecimal(resultList.size()));
    }

    @Override
    public void editPassword(String username,String pwd1, String pwd2, String pwd3) {
        User user = userRepository.findUserByLoginNameAndIsDelete(username,0);
        if(!pwd2.equals(pwd3)){
            throw new UnimaxException("新密码与确认密码不一致!");
        }
        if(!pwd1.equals(user.getPassword())){
            throw new UnimaxException("登录密码错误!");
        }
        user.setPassword(pwd2);
        userRepository.save(user);
    }


}
