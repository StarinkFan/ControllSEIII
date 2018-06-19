package com.software2.demo.security;

import com.software2.demo.bean.User;
import com.software2.demo.dao.UserDataService;
import com.software2.demo.service.Impl.UserBLImpl;
import com.software2.demo.service.UserBLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Component
public class myUserDetailsService implements UserDetailsService{

    @Autowired
    private UserBLService userBLService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u=userBLService.getSingle(s);
        if(u==null){
            throw new UsernameNotFoundException("UserName " + s + " not found");
        }
        return new SecurityUser(u);
    }
}
