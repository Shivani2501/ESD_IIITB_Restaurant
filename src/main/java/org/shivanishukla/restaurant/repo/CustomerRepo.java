package org.shivanishukla.restaurant.repo;

import org.shivanishukla.restaurant.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
