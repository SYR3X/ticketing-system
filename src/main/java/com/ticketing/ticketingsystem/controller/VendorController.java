package com.ticketing.ticketingsystem.controller;

import com.ticketing.ticketingsystem.vendor.VendorManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    private final VendorManager vendorManager;

    public VendorController(VendorManager vendorManager) {
        this.vendorManager = vendorManager;
    }

    @PostMapping
    public ResponseEntity<String> startVendor(@RequestParam String vendorId,
                                              @RequestParam int releaseRate) {
        vendorManager.startVendor(vendorId, releaseRate);
        return ResponseEntity.ok("Vendor started successfully");
    }

    @DeleteMapping("/{vendorId}")
    public ResponseEntity<String> stopVendor(@PathVariable String vendorId) {
        vendorManager.stopVendor(vendorId);
        return ResponseEntity.ok("Vendor stopped successfully");
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Integer>> getVendorStats() {
        return ResponseEntity.ok(vendorManager.getVendorStats());
    }
}
