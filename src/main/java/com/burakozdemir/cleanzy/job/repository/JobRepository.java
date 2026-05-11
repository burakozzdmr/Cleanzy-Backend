package com.burakozdemir.cleanzy.job.repository;

import com.burakozdemir.cleanzy.common.util.JobStatusType;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import com.burakozdemir.cleanzy.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCustomer_User_Id(Long userId);

    List<Job> findByCustomer_User_IdAndStatus(Long userId, JobStatusType status);

    List<Job> findByAssignedCleaner_User_Id(Long userId);

    List<Job> findByAssignedCleaner_User_IdAndStatus(Long userId, JobStatusType status);

    long countByCustomer(Customer customer);

    long countByAssignedCleaner_IdAndStatus(Long cleanerId, JobStatusType status);
}
