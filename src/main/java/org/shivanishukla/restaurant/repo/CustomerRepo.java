package org.shivanishukla.restaurant.repo;

import org.shivanishukla.restaurant.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
   Optional<Customer> findByEmailAndPassword(String email, String password);
}
