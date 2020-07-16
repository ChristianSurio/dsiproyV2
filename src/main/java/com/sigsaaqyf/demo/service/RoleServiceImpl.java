package com.sigsaaqyf.demo.service;

import com.sigsaaqyf.demo.entity.Role;
import com.sigsaaqyf.demo.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Iterable<Role> findAllRoles() {
        return roleRepository.findAll();
    }
    
}