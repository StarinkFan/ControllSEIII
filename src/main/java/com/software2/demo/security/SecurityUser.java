package com.software2.demo.security;

import com.software2.demo.bean.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUser extends User implements UserDetails{
    public SecurityUser(User u){
        super(u.getID(),u.getName(),u.getPassword(),u.getSex(),u.getWechat(),u.getAddress(),u.getIntroduction(),u.getCredit(),u.getEarncredit(),u.getRanking(),u.getListOfCRecord(),u.getListOfITask(),u.getListOfWTask(),u.getListOfTags(),u.getListOfTitles(),u.getHeadShotUrl(),u.getOnlinestatus(),u.getSessionid());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("USER");
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getID();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object rhs){
        if(rhs instanceof SecurityUser){
            return getUsername().equals(((SecurityUser)rhs).getUsername());
        }
        return false;
    }
}
