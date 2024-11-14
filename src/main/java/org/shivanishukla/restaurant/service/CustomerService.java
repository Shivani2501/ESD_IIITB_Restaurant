package org.shivanishukla.restaurant.service;

import org.shivanishukla.restaurant.dto.CustomerRequest;
import org.shivanishukla.restaurant.entity.Customer;
import org.shivanishukla.restaurant.mapper.CustomerMapper;
import org.shivanishukla.restaurant.repo.CustomerRepo;
import org.shivanishukla.restaurant.helper.EncryptionService;
import org.shivanishukla.restaurant.helper.JWTHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.shivanishukla.restaurant.dto.LoginRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CustomerService {
 private final CustomerRepo repo;
 private final CustomerMapper mapper;
    private final JWTHelper jwtHelper;
    private final EncryptionService encryptionService;

 public String createCustomer(CustomerRequest request){
     Customer customer=mapper.toEntity(request);
     customer.setPassword(encryptionService.encode(request.password()));
     repo.save(customer);
     return "Created";
 }
 public Customer getCustomer(String email){
     return repo.findByEmail(email).orElseThrow(()->new RuntimeException("Customer not found"));
 }

    public String loginCustomer(LoginRequest loginRequest) {
        Customer customer = getCustomer(loginRequest.email());
        if (encryptionService.validates(loginRequest.password(), customer.getPassword())) {
            return jwtHelper.generateToken(customer.getEmail());
        }
        return "Wrong password or Email";
    }

}
