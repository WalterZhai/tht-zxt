package com.cimctht.thtzxt.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class DefaultLoginProvider implements AuthenticationProvider {

    @Autowired
    private DefaultProviderService defaultProviderService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();// 这个获取表单输入中返回的用户名;
        String password = (String)authentication.getCredentials();// 这个是表单中输入的密码；

        UserDetails user = defaultProviderService.loadUserByUsername(userName);

        if(user == null){
            return null;
        }

        if(!password.equals(user.getPassword())){
            return null;
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(user, password, authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        //根据Token的类匹配，决定哪个Provider去验证登录
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
