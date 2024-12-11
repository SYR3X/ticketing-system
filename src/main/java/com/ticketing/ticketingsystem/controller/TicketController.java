package com.ticketing.ticketingsystem.controller;

import com.ticketing.ticketingsystem.model.Ticket;
import com.ticketing.ticketingsystem.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<String> purchaseTicket(@PathVariable Long id, @RequestParam int quantity) {
        boolean success = ticketService.purchaseTicket(id, quantity);
        return success
                ? ResponseEntity.ok("Purchase successful")
                : ResponseEntity.badRequest().body("Purchase failed");
    }
}
