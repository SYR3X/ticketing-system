package com.ticketing.ticketingsystem.controller;

import com.ticketing.ticketingsystem.customer.CustomerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerManager customerManager;

    @PostMapping
    public ResponseEntity<String> startCustomer(
            @RequestParam String customerId,
            @RequestParam int maxPurchaseSize,
            @RequestParam int retrievalRate) {
        customerManager.startCustomer(customerId, maxPurchaseSize, retrievalRate);
        return ResponseEntity.ok("Customer started successfully");
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> stopCustomer(@PathVariable String customerId) {
        customerManager.stopCustomer(customerId);
        return ResponseEntity.ok("Customer stopped successfully");
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Integer>> getCustomerStats() {
        return ResponseEntity.ok(customerManager.getCustomerStats());
    }
}
