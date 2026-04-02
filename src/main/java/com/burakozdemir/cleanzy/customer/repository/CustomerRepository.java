package com.burakozdemir.cleanzy.customer.repository;

import com.burakozdemir.cleanzy.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> { }
