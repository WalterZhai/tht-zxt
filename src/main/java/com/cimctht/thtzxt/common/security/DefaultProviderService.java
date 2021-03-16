package com.cimctht.thtzxt.common.security;

import com.cimctht.thtzxt.system.entity.Role;
import com.cimctht.thtzxt.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Transactional
@Service
public class DefaultProviderService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.cimctht.thtzxt.system.entity.User user = userRepository.findUserByLoginNameAndIsLockedAndIsDelete(username,0, 0);
        if(user == null){
            return null;
        }
        String dbPassword = user.getPassword();
        // 获取用户的角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色必须以`ROLE_`开头
        List<Role> listRoles = user.getRoles();
        for(Role r : listRoles) {
            authorities.add(new SimpleGrantedAuthority(r.getCode()));
        }
        return new User(username,dbPassword,true,true,true,true,authorities);
    }
}
