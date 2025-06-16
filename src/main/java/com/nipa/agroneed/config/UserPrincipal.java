package com.nipa.agroneed.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nipa.agroneed.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private Long id;
    private String phone;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;//role

    public UserPrincipal(Long id, String phone, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        try {
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            user.getRoles().forEach(role -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
                grantedAuthorityList.add(simpleGrantedAuthority); //spring security k ble dichi ki ki role hobe
            });
            return new UserPrincipal(user.getId(), user.getPhone(), user.getPasswordHash(), grantedAuthorityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}