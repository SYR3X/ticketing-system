package com.ticketing.ticketingsystem.model;

import lombok.Data;
import java.util.List;
//Main configuration data class that holds all system settings
@Data
public class ConfigData {
    private int maxTicketCapacity;
    private List<VendorConfig> vendors;
    private List<CustomerConfig> customers;
}
