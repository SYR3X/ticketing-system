package com.ticketing.ticketingsystem.service;

import com.ticketing.ticketingsystem.pool.TicketPool;
import com.ticketing.ticketingsystem.customer.CustomerManager;
import com.ticketing.ticketingsystem.vendor.VendorManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
//service responsible for monitoring and logging system status
@Slf4j
@Service
@EnableScheduling
public class MonitoringService {
    private final TicketPool ticketPool;
    private final CustomerManager customerManager;
    private final VendorManager vendorManager;

    public MonitoringService(TicketPool ticketPool,
                             CustomerManager customerManager,
                             VendorManager vendorManager) {
        this.ticketPool = ticketPool;
        this.customerManager = customerManager;
        this.vendorManager = vendorManager;
    }
//Logs system status every 5 seconds
    @Scheduled(fixedRate = 5000)
    public void monitorSystem() {
        log.info("System Status:");
        log.info("Available Tickets: {}", ticketPool.getAvailableTickets());
        log.info("Customer Stats: {}", customerManager.getCustomerStats());
        log.info("Vendor Stats: {}", vendorManager.getVendorStats());
    }
}
