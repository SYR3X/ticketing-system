package com.ticketing.ticketingsystem.customer;

import com.ticketing.ticketingsystem.pool.TicketPool;
import lombok.Getter;
import org.springframework.scheduling.annotation.Async;

@Getter
public class Customer implements Runnable {
    private final String customerId;
    private final int maxHoldingCapacity;
    private final int retrievalRate;
    private final TicketPool ticketPool;
    private volatile boolean running = true;
    private int ticketsHeld = 0;

    public Customer(String customerId, int maxHoldingCapacity, int retrievalRate, TicketPool ticketPool) {
        this.customerId = customerId;
        this.maxHoldingCapacity = maxHoldingCapacity;
        this.retrievalRate = retrievalRate;
        this.ticketPool = ticketPool;
    }

    @Async
    @Override
    public void run() {
        while (running) {
            try {
                if (ticketsHeld < maxHoldingCapacity) {
                    boolean success = ticketPool.purchaseTicket(customerId);
                    if (success) {
                        ticketsHeld++;
                    }
                }
                Thread.sleep(1000L / retrievalRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }
}
