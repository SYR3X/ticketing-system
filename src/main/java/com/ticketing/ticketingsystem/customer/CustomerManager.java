package com.ticketing.ticketingsystem.customer;

import com.ticketing.ticketingsystem.pool.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CustomerManager {
    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private TicketPool ticketPool;

    public void startCustomer(String customerId, int maxHoldingCapacity, int retrievalRate) {
        Customer customer = new Customer(customerId, maxHoldingCapacity, retrievalRate, ticketPool);
        customers.put(customerId, customer);
        executorService.submit(customer);
    }

    public void stopCustomer(String customerId) {
        Customer customer = customers.get(customerId);
        if (customer != null) {
            customer.stop();
            customers.remove(customerId);
        }
    }

    public Map<String, Integer> getCustomerStats() {
        Map<String, Integer> stats = new ConcurrentHashMap<>();
        customers.forEach((id, customer) ->
                stats.put(id, customer.getTicketsHeld())
        );
        return stats;
    }

    public void reset() {
        customers.values().forEach(Customer::stop);
        customers.clear();
    }
}
