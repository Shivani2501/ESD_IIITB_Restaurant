package org.shivanishukla.restaurant.mapper;

import org.shivanishukla.restaurant.entity.Customer;
import org.shivanishukla.restaurant.dto.CustomerRequest;
import org.springframework.stereotype.Service;
@Service
public class CustomerMapper {
    public Customer toEntity(CustomerRequest request)
    {
      return Customer.builder()
              .firstName(request.firstName())
              .lastName(request.lastName())
              .email(request.email())
              .password(request.password())
              .build();
    }
}
