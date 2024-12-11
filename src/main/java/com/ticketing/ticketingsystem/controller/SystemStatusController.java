package com.ticketing.ticketingsystem.controller;

import com.ticketing.ticketingsystem.pool.TicketPool;
import com.ticketing.ticketingsystem.customer.CustomerManager;
import com.ticketing.ticketingsystem.vendor.VendorManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemStatusController {
    private final TicketPool ticketPool;
    private final VendorManager vendorManager;
    private final CustomerManager customerManager;

    public SystemStatusController(TicketPool ticketPool, VendorManager vendorManager, CustomerManager customerManager) {
        this.ticketPool = ticketPool;
        this.vendorManager = vendorManager;
        this.customerManager = customerManager;
    }

    @PostMapping("/capacity")
    public ResponseEntity<Map<String, Object>> setCapacity(@RequestParam int capacity) {
        ticketPool.setMaxCapacity(capacity);
        Map<String, Object> response = new HashMap<>();
        response.put("capacity", ticketPool.getMaxCapacity());
        response.put("availableTickets", ticketPool.getAvailableTickets());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getSystemStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("maxCapacity", ticketPool.getMaxCapacity());
        status.put("availableTickets", ticketPool.getAvailableTickets());
        status.put("vendorStats", vendorManager.getVendorStats());
        status.put("customerStats", customerManager.getCustomerStats());
        return ResponseEntity.ok(status);
    }
}
