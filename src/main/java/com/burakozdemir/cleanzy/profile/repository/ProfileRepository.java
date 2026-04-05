package com.burakozdemir.cleanzy.profile.repository;

import com.burakozdemir.cleanzy.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<User, Long> { }
