package com.marcos.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcos.security.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
