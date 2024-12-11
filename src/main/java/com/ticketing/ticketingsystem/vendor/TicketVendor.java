package com.ticketing.ticketingsystem.vendor;

import com.ticketing.ticketingsystem.pool.TicketPool;
import lombok.Getter;
import org.springframework.scheduling.annotation.Async;

@Getter
public class TicketVendor implements Runnable {
    private final String vendorId;
    private final int releaseRate;
    private final TicketPool ticketPool;
    private volatile boolean running = true;
    private int ticketsReleased = 0;

    public TicketVendor(String vendorId, int releaseRate, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.releaseRate = releaseRate;
        this.ticketPool = ticketPool;
    }

    @Async
    @Override
    public void run() {
        while (running) {
            try {
                if (ticketPool.getAvailableTickets() < ticketPool.getMaxCapacity()) {
                    ticketPool.releaseTicket(vendorId);
                    ticketsReleased++;
                }
                Thread.sleep(1000L / releaseRate);
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
