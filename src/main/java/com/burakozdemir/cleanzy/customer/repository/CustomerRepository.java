package com.burakozdemir.cleanzy.customer.repository;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser(User user);
}
