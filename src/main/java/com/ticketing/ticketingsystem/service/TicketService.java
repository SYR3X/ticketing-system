package com.ticketing.ticketingsystem.service;

import com.ticketing.ticketingsystem.model.Ticket;
import com.ticketing.ticketingsystem.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public synchronized boolean purchaseTicket(Long ticketId, int quantity) {
        return ticketRepository.findById(ticketId)
                .filter(ticket -> ticket.isAvailable() && ticket.getQuantity() >= quantity)
                .map(ticket -> {
                    ticket.setQuantity(ticket.getQuantity() - quantity);
                    ticket.setAvailable(ticket.getQuantity() > 0);
                    ticketRepository.save(ticket);
                    return true;
                })
                .orElse(false);
    }
}
