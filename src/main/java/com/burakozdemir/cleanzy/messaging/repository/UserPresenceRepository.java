package com.burakozdemir.cleanzy.messaging.repository;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.messaging.entity.UserPresence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPresenceRepository extends JpaRepository<UserPresence, Long> {

    Optional<UserPresence> findByUser(User user);
}
