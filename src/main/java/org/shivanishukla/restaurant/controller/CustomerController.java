package org.shivanishukla.restaurant.controller;

import org.shivanishukla.restaurant.dto.CustomerRequest;
import org.shivanishukla.restaurant.dto.CustomerResponse;
import org.shivanishukla.restaurant.entity.Customer;
import org.shivanishukla.restaurant.helper.JWTHelper;
import org.shivanishukla.restaurant.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.shivanishukla.restaurant.dto.LoginRequest;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")

public class CustomerController {
    private final CustomerService customerservice;
    private final JWTHelper jwtHelper;
    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request){
        return ResponseEntity.ok(customerservice.createCustomer(request));
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateCustomer(@RequestBody @Valid CustomerRequest request, HttpServletRequest httpRequest) {
        String email = request.email(); // Email from the request body
        String authorizationHeader = httpRequest.getHeader("Authorization");

        // Check for Authorization header and token prefix
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing or invalid token");
        }

        String token = authorizationHeader.substring(7); // Extract the token from "Bearer {token}"
        String tokenEmail = jwtHelper.extractEmail(token);

        // Validate the token and check if the email in the token matches the request email
        if (!jwtHelper.validateToken(token, tokenEmail) || !tokenEmail.equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: Invalid credentials or email mismatch");
        }

        // Proceed with updating the customer if authorized
        String result = customerservice.updateCustomer(email, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String email, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing or invalid token");
        }

        String token = authorizationHeader.substring(7);
        String tokenEmail = jwtHelper.extractEmail(token);

        // Check if the email in the token matches the email provided in the request
        if (!jwtHelper.validateToken(token, tokenEmail) || !tokenEmail.equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: Invalid credentials or email mismatch");
        }

        try {
            customerservice.deleteCustomer(email);
            return ResponseEntity.ok("Customer deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String email, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);
        String tokenEmail = jwtHelper.extractEmail(token);

        if (!jwtHelper.validateToken(token, tokenEmail) || !tokenEmail.equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        CustomerResponse customerResponse = customerservice.getCustomerResponseByEmail(email);
        return ResponseEntity.ok(customerResponse);
    }

}


