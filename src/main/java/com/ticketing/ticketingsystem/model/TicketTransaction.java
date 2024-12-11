package com.ticketing.ticketingsystem.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TicketTransaction {
    private String ticketId;
    private String vendorId;
    private String customerId;
    private LocalDateTime releaseTime;
    private LocalDateTime purchaseTime;
    private TransactionType type;

    public enum TransactionType {
        RELEASE,
        PURCHASE
    }
}
