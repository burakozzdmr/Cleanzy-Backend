package com.burakozdemir.cleanzy.favorite.repository;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByUser(User user);
    boolean existsByUserAndFavoritedUser(User user, User favoritedUser);
    void deleteByUserAndFavoritedUser(User user, User favoritedUser);
}
