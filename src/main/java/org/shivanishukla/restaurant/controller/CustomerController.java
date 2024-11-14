package org.shivanishukla.restaurant.controller;

import org.shivanishukla.restaurant.dto.CustomerRequest;
import org.shivanishukla.restaurant.dto.CustomerResponse;
import org.shivanishukla.restaurant.entity.Customer;
import org.shivanishukla.restaurant.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.shivanishukla.restaurant.dto.LoginRequest;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")

public class CustomerController {
    private final CustomerService customerservice;
    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request){
        return ResponseEntity.ok(customerservice.createCustomer(request));
    }


    }


