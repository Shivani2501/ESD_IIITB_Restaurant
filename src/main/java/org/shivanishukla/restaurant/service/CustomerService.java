package org.shivanishukla.restaurant.service;

import org.shivanishukla.restaurant.dto.CustomerRequest;
import org.shivanishukla.restaurant.dto.CustomerResponse;
import org.shivanishukla.restaurant.entity.Customer;
import org.shivanishukla.restaurant.mapper.CustomerMapper;
import org.shivanishukla.restaurant.repo.CustomerRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import org.shivanishukla.restaurant.dto.LoginRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CustomerService {
 private final CustomerRepo repo;
 private final CustomerMapper mapper;
 public String createCustomer(CustomerRequest request){
     Customer customer=mapper.toEntity(request);
     repo.save(customer);
     return "Created";
 }

 public Optional<CustomerResponse> loginCustomer(LoginRequest loginRequest){
     Optional<Customer> customer =repo.findByEmailAndPassword(loginRequest.email(), loginRequest.password());
     return customer.map(mapper::toResponse);
 }

}
