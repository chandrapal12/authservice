package org.example.service;

import org.example.entities.Role;
import org.example.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
public class CustomUserDetails extends User implements UserDetails {
    private String username;
    private String password;

    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User byUsername){
        this.username = byUsername.getUsername();
        this.password = byUsername.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();

        for(Role role : byUsername.getRoles()){
            auths.add(new SimpleGrantedAuthority(role.getRoleName().toUpperCase()));
        }
        this.authorities=auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
