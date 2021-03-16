package com.cimctht.thtzxt.common.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public class WxLoginFilter  extends AbstractAuthenticationProcessingFilter {

    // @Autowired
    // private WechatService wechatService;
    //
    // @Autowired
    // private UserRepository userRepository;
    //
    // @Autowired
    // private RoleRepository roleRepository;


    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean postOnly = false;

    protected WxLoginFilter() {
        super(new AntPathRequestMatcher("/wxLogin", "GET"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }


        String username = "";

        // //首先遍历cookie查看是否有username
        // Cookie[] cookies = request.getCookies();
        // if(cookies!=null){
        //     for(Cookie cookie : cookies){
        //         if(cookie.getName().equals("username")){
        //             username =  cookie.getValue();
        //         }
        //     }
        // }
        //
        //
        // if(StringUtils.isEmpty(username)){
        //     String code = request.getParameter("code");
        //     String state = request.getParameter("state");
        //     String[] params = state.split(";");
        //
        //
        //     String token = wechatService.getToken(params[0]);
        //     String userid = wechatService.getUserInfoByUserid(code,token);
        //
        //     //失败有可能是token维护过期，重新申请后，如果还是有问题，就验证失败
        //     if(userid==null){
        //         token = wechatService.getTokenRequest(params[0]);
        //         userid = wechatService.getUserInfoByUserid(code,token);
        //     }
        //     //如果userid获取成功，将cookie写入response
        //     if(userid!=null){
        //         //写个cookie给client，以便下次申请时用
        //         Cookie cookie  =  new Cookie("username",userid);
        //         cookie.setMaxAge(365*24*3600);
        //         response.addCookie(cookie);
        //         username = userid;
        //     }
        // }
        //
        // //每次都判断session中user是否存在
        // if(StringUtils.isNotEmpty(username) && request.getSession().getAttribute("user")==null){
        //     HttpSession session = request.getSession();
        //     User user = Cache.users.get(username);//userRepository.queryUserByLoginNameAndIsDeleteAndIsLocked(username,0,0);
        //     List<String> listRoleCodes = new ArrayList<String>();
        //     List<Role> listRoles = Cache.roles.get(username);//roleRepository.queryRolesByUserLoginname(username);
        //     for(Role r : listRoles) {
        //         listRoleCodes.add(r.getCode());
        //     }
        //     session.setAttribute("user",user);
        //     session.setAttribute("roles",listRoleCodes);
        // }

        String password = "";

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        // 该处对第一步的token进行包装，用于在AuthenticationProvider里面校验是否该AuthenticationProvider拦截校验
        WxLoginAuthenticationToken authRequest = new WxLoginAuthenticationToken(username, password);

        this.setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }

    protected void setDetails(HttpServletRequest request, WxLoginAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
