package com.ticketing.ticketingsystem.vendor;

import com.ticketing.ticketingsystem.pool.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class VendorManager {
    private final Map<String, TicketVendor> vendors = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private TicketPool ticketPool;

    public void startVendor(String vendorId, int releaseRate) {
        TicketVendor vendor = new TicketVendor(vendorId, releaseRate, ticketPool);
        vendors.put(vendorId, vendor);
        executorService.submit(vendor);
    }

    public void stopVendor(String vendorId) {
        TicketVendor vendor = vendors.get(vendorId);
        if (vendor != null) {
            vendor.stop();
            vendors.remove(vendorId);
        }
    }

    public Map<String, Integer> getVendorStats() {
        Map<String, Integer> stats = new ConcurrentHashMap<>();
        vendors.forEach((id, vendor) ->
                stats.put(id, vendor.getTicketsReleased())
        );
        return stats;
    }

    public void reset() {
        vendors.values().forEach(TicketVendor::stop);
        vendors.clear();
    }
}
