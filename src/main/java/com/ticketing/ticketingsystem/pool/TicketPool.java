package com.ticketing.ticketingsystem.pool;

import com.ticketing.ticketingsystem.service.TicketTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class TicketPool {
    private int availableTickets = 0;
    private int maxCapacity = 100;
    private final Queue<String> availableTicketIds = new ConcurrentLinkedQueue<>();

    @Autowired
    private TicketTransactionService transactionService;

    public synchronized void releaseTicket(String vendorId) {
        if (availableTickets < maxCapacity) {
            String ticketId = transactionService.generateUniqueTicketId();
            availableTicketIds.offer(ticketId);
            availableTickets++;
            transactionService.recordRelease(ticketId, vendorId);
        }
    }

    public synchronized boolean purchaseTicket(String customerId) {
        if (availableTickets > 0) {
            String ticketId = availableTicketIds.poll();
            availableTickets--;
            transactionService.recordPurchase(ticketId, customerId);
            return true;
        }
        return false;
    }


    public synchronized int getAvailableTickets() {
        return availableTickets;
    }

    public synchronized void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized int getMaxCapacity() {
        return maxCapacity;
    }

    public synchronized void reset() {
        this.availableTickets = 0;
    }
}
