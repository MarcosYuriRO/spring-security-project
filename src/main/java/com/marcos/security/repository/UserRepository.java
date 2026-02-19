package com.marcos.security.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcos.security.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
