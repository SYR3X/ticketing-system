package com.ticketing.ticketingsystem;

import com.google.gson.Gson;
import com.ticketing.ticketingsystem.model.ConfigData;
import com.ticketing.ticketingsystem.model.CustomerConfig;
import com.ticketing.ticketingsystem.model.VendorConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CliRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final Gson gson = new Gson();
    private final String CONFIG_FILE = "ticket_config.json";

    public ConfigData initializeConfig() {
        try {
            if (new File(CONFIG_FILE).exists()) {
                System.out.println("Found existing configuration. Would you like to use it? (yes/no)");
                String response = scanner.nextLine().trim().toLowerCase();
                while (!response.equals("yes") && !response.equals("no")) {
                    System.out.println("Please enter 'yes' or 'no'");
                    response = scanner.nextLine().trim().toLowerCase();
                }

                if (response.equals("yes")) {
                    return loadConfig();
                }
            }
            return createNewConfig();
        } catch (Exception e) {
            System.out.println("Error during initialization: " + e.getMessage());
            System.out.println("Creating new configuration...");
            return createNewConfig();
        }
    }

    private ConfigData createNewConfig() {
        ConfigData config = new ConfigData();

        config.setMaxTicketCapacity(getValidIntInput(
                "Enter max ticket capacity:",
                input -> input > 0
        ));

        int vendorCount = getValidIntInput(
                "Enter number of vendors:",
                input -> input > 0
        );

        ArrayList<VendorConfig> vendors = new ArrayList<>();
        for (int i = 0; i < vendorCount; i++) {
            vendors.add(createVendorConfig(i + 1));
        }

        int customerCount = getValidIntInput(
                "Enter number of customers:",
                input -> input > 0
        );

        ArrayList<CustomerConfig> customers = new ArrayList<>();
        for (int i = 0; i < customerCount; i++) {
            customers.add(createCustomerConfig(i + 1));
        }

        config.setVendors(vendors);
        config.setCustomers(customers);
        saveConfig(config);
        return config;
    }

    private VendorConfig createVendorConfig(int index) {
        VendorConfig vendor = new VendorConfig();
        vendor.setVendorId(getValidInput(
                "Enter Vendor " + index + " ID:",
                "Vendor ID cannot be empty",
                input -> !input.trim().isEmpty()
        ));

        vendor.setReleaseRate(getValidIntInput(
                "Enter Vendor " + index + " release rate (tickets/second):",
                input -> input > 0
        ));

        return vendor;
    }

    private CustomerConfig createCustomerConfig(int index) {
        CustomerConfig customer = new CustomerConfig();
        customer.setCustomerId(getValidInput(
                "Enter Customer " + index + " ID:",
                "Customer ID cannot be empty",
                input -> !input.trim().isEmpty()
        ));

        customer.setRetrievalRate(getValidIntInput(
                "Enter Customer " + index + " retrieval rate (tickets/second):",
                input -> input > 0
        ));

        customer.setMaxHoldingCapacity(getValidIntInput(
                "Enter Customer " + index + " max holding capacity:",
                input -> input > 0
        ));

        return customer;
    }

    private String getValidInput(String prompt, String errorMessage, InputValidator<String> validator) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if (validator.isValid(input)) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    private int getValidIntInput(String prompt, InputValidator<Integer> validator) {
        while (true) {
            try {
                System.out.println(prompt);
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (validator.isValid(input)) {
                    return input;
                }
                System.out.println("Please enter a positive number");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    private void saveConfig(ConfigData config) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(config, writer);
            System.out.println("Configuration saved successfully");
        } catch (Exception e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    private ConfigData loadConfig() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            ConfigData config = gson.fromJson(reader, ConfigData.class);
            System.out.println("Configuration loaded successfully");
            return config;
        } catch (Exception e) {
            System.out.println("Error loading configuration: " + e.getMessage());
            return createNewConfig();
        }
    }

    @FunctionalInterface
    private interface InputValidator<T> {
        boolean isValid(T input);
    }
}
