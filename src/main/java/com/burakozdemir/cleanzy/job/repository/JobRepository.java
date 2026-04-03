package com.burakozdemir.cleanzy.job.repository;

import com.burakozdemir.cleanzy.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> { }
