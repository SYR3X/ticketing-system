package com.ticketing.ticketingsystem.controller;

import com.ticketing.ticketingsystem.model.TicketTransaction;
import com.ticketing.ticketingsystem.service.TicketTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TicketTransactionController {
    private final TicketTransactionService transactionService;

    public TicketTransactionController(TicketTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TicketTransaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
}
