package com.ticketing.ticketingsystem;

import com.ticketing.ticketingsystem.model.ConfigData;
import com.ticketing.ticketingsystem.pool.TicketPool;
import com.ticketing.ticketingsystem.customer.CustomerManager;
import com.ticketing.ticketingsystem.vendor.VendorManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TicketingSystemApplication {
    public static void main(String[] args) {
        // Create and run CLI before Spring Boot starts
        CliRunner cliRunner = new CliRunner();
        ConfigData config = cliRunner.initializeConfig();

        // Start Spring Boot with the configuration
        ConfigurableApplicationContext context = SpringApplication.run(TicketingSystemApplication.class, args);

        // Get necessary beans and configure them
        TicketPool ticketPool = context.getBean(TicketPool.class);
        VendorManager vendorManager = context.getBean(VendorManager.class);
        CustomerManager customerManager = context.getBean(CustomerManager.class);

        // Apply configuration
        ticketPool.setMaxCapacity(config.getMaxTicketCapacity());

        // Start vendors
        config.getVendors().forEach(vendor ->
                vendorManager.startVendor(vendor.getVendorId(), vendor.getReleaseRate())
        );

        // Start customers
        config.getCustomers().forEach(customer ->
                customerManager.startCustomer(
                        customer.getCustomerId(),
                        customer.getMaxHoldingCapacity(),
                        customer.getRetrievalRate()
                )
        );
    }
}
