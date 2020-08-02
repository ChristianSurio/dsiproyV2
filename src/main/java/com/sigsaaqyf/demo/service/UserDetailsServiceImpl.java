package com.sigsaaqyf.demo.service;

import java.util.HashSet;
import java.util.Set;

import com.sigsaaqyf.demo.entity.User;
import com.sigsaaqyf.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String carnet) throws UsernameNotFoundException {
        com.sigsaaqyf.demo.entity.User appUser = userRepository.findByCarnet(carnet).orElseThrow(()->new UsernameNotFoundException("Login Carnet inválido") );
        
        Set<GrantedAuthority> grantList = new HashSet<GrantedAuthority>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(appUser.getRoles().getNombre());
        grantList.add(grantedAuthority);
        UserDetails user = (UserDetails) new org.springframework.security.core.userdetails.User(carnet, appUser.getPassword(), grantList);
        
        return user;
    }
    
}