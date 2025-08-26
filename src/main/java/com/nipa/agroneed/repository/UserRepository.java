package com.nipa.agroneed.repository;

import com.nipa.agroneed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByNameAndStatus(String name, Integer status);
    User findByPhoneAndStatus(String phone, Integer status);

    User findByIdAndStatus(Long userId, Integer status);
}
