package org.shivanishukla.restaurant.service;

import org.shivanishukla.restaurant.dto.CustomerRequest;
import org.shivanishukla.restaurant.dto.CustomerResponse;
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

    public String updateCustomer(String email, CustomerRequest updatedRequest) {
        Customer existingCustomer = getCustomer(email);

        existingCustomer.setFirstName(updatedRequest.firstName());
        existingCustomer.setLastName(updatedRequest.lastName());
        existingCustomer.setCity(updatedRequest.city());
        existingCustomer.setAddress(updatedRequest.Address());
        existingCustomer.setPincode(updatedRequest.pincode());

        // Update password only if it's provided in the request
        if (updatedRequest.password() != null && !updatedRequest.password().isEmpty()) {
            existingCustomer.setPassword(encryptionService.encode(updatedRequest.password()));
        }

        repo.save(existingCustomer);  // Save the updated customer in the database
        return "Customer updated successfully";
    }

    public CustomerResponse deleteCustomer(String email) {
        Customer customer = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return mapper.toResponse(customer);
    }


}
