package com.ticketing.ticketingsystem.model;

import lombok.Data;
//customer settings
@Data
public class CustomerConfig {
    private String customerId;
    private int retrievalRate;
    private int maxHoldingCapacity;
}
