package com.cimctht.thtzxt.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultLoginProvider defaultLoginProvider;
    @Autowired
    private WxLoginProvider wxLoginProvider;
    @Autowired
    private DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;
    @Autowired
    private DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;
    @Autowired
    private WxAuthenticationSuccessHandler wxAuthenticationSuccessHandler;
    @Autowired
    private WxAuthenticationFailureHandler wxAuthenticationFailureHandler;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(defaultLoginProvider);
        auth.authenticationProvider(wxLoginProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //拦截器需要在此注册
        http.addFilterBefore(usernamePasswordAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);
        http.addFilterBefore(wxLoginFilter(), AbstractPreAuthenticatedProcessingFilter.class);

        http.cors()
                .and().authorizeRequests() // 通过authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护。
                .antMatchers("/layui/**","/assets/**","/login","/error","/*.txt").permitAll() // /login不需要任何认证就可以访问。
                .anyRequest().authenticated() // 其他的路径都必须通过身份验证
                .and().formLogin() // 当需要用户登录时候
                .loginPage("/login")// 转到的登录页面
                .permitAll()
                .and().logout().permitAll();
        http.csrf().disable();//同源限制取消
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");// 防止多处登录
        http.headers().frameOptions().disable();//禁止iframe嵌套,关闭
    }

    //这个必须重写，才能使用AuthenticationManager，在成员变量注入进来，再注入过滤器中
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //下面就是默认的过滤器UsernamePasswordAuthenticationFilter
    //配置一下拦截地址、认证成功失败处理器、authenticationManager

    /**
     * @comment 默认用户名密码认证过滤器
     * @author Walter(翟笑天)
     * @date 2021/3/13
     */
    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();

        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(defaultAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(defaultAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/authentication");
        return filter;
    }

    //下面就是自定义的过滤器，配置一下拦截地址、认证成功失败处理器、authenticationManager
    /**
     * @comment 微信登录过滤器
     * @author Walter(翟笑天)
     * @date 2021/3/13
     */
    @Bean
    public WxLoginFilter wxLoginFilter() {
        WxLoginFilter filter = new WxLoginFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(wxAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(wxAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/wxLogin");
        return filter;
    }



}
