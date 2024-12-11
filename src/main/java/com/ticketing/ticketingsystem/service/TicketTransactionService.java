package com.ticketing.ticketingsystem.service;

import com.ticketing.ticketingsystem.model.TicketTransaction;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDateTime;

@Service
public class TicketTransactionService {
    private final Map<String, TicketTransaction> transactions = new ConcurrentHashMap<>();
    private long ticketIdCounter = 0;

    public synchronized String generateUniqueTicketId() {
        return "TICKET-" + (++ticketIdCounter);
    }

    public void recordRelease(String ticketId, String vendorId) {
        TicketTransaction transaction = new TicketTransaction();
        transaction.setTicketId(ticketId);
        transaction.setVendorId(vendorId);
        transaction.setReleaseTime(LocalDateTime.now());
        transaction.setType(TicketTransaction.TransactionType.RELEASE);
        transactions.put(ticketId, transaction);
    }

    public void recordPurchase(String ticketId, String customerId) {
        TicketTransaction transaction = transactions.get(ticketId);
        if (transaction != null) {
            transaction.setCustomerId(customerId);
            transaction.setPurchaseTime(LocalDateTime.now());
            transaction.setType(TicketTransaction.TransactionType.PURCHASE);
        }
    }

    public List<TicketTransaction> getAllTransactions() {
        return new ArrayList<>(transactions.values());
    }
}
