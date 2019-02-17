package com.glqdlt.ex.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserServices userServices;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User aa = userServices.findById(s);
        if(aa == null){
            throw new UsernameNotFoundException(s+"is Not Founded User!");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(aa.getId())
                .password(aa.getPassword())
                .passwordEncoder((s1) -> s1)
                .roles(aa.getRole())
                .build();
    }
}
