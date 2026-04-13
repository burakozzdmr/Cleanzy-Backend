package com.burakozdemir.cleanzy.favorite.repository;

import com.burakozdemir.cleanzy.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<User, Long> { }
