package com.jonnie.ecommerce.controllers;

import com.jonnie.ecommerce.customer.CustomerRequest;
import com.jonnie.ecommerce.customer.CustomerResponse;
import com.jonnie.ecommerce.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    // method to create a customer
    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest customerRequest
            ) {
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    // method to find all customers
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    // method to check if a customer exists
    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> customerExists(
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(customerService.customerExists(customerId));
    }
    //method to find a customer by id
    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findCustomerById(
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(customerService.findCustomerById(customerId));
    }
    // method to update a customer
    @PutMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest customerRequest
    ) {
        customerService.updateCustomer(customerRequest);
        return ResponseEntity.accepted().build();
    }

    // method to delete a customer
    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable("customer-id") String customerId
    ) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }

}
