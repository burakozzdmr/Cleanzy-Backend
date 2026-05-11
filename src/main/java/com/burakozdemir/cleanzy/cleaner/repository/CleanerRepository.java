package com.burakozdemir.cleanzy.cleaner.repository;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.common.util.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CleanerRepository extends JpaRepository<Cleaner, Long> {
    Optional<Cleaner> findByUser(User user);
    List<Cleaner> findByServicesContaining(ServiceType service);
}
