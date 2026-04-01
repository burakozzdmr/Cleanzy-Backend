package com.burakozdemir.cleanzy.auth.repository;

import com.burakozdemir.cleanzy.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Long, User> { }
