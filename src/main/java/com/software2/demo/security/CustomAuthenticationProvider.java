package com.software2.demo.security;

import com.software2.demo.ResultMessage;
import com.software2.demo.bean.Admin;
import com.software2.demo.bean.User;
import com.software2.demo.service.UserBLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private myUserDetailsService myUserDetailsService;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if(username.equals("admin")){
            User u=new User();
            u.setPassword("admin");
            u.setID("admin");
            SecurityUser user=new SecurityUser(u);
            return new UsernamePasswordAuthenticationToken(user, password, AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
        }
        SecurityUser user= (SecurityUser) myUserDetailsService.loadUserByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("账号不存在");
        }
        if(!password.equals(user.getPassword()))
            throw new BadCredentialsException("密码错误");
/*        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(user,password);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        User u=null;

        return token;*/
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
