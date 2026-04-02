package com.burakozdemir.cleanzy.cleaner.repository;

import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CleanerRepository extends JpaRepository<Cleaner, Long> { }
