package com.sigsaaqyf.demo.repository;


import com.sigsaaqyf.demo.entity.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    public Role findByNombre(String nombre);
}