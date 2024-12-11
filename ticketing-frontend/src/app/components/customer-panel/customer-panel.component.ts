import { Component, OnInit } from '@angular/core';
import { TicketService } from '../../services/ticket.service';

@Component({
  selector: 'app-customer-panel',
  templateUrl: './customer-panel.component.html',
  styleUrls: ['./customer-panel.component.scss']
})
export class CustomerPanelComponent implements OnInit {
  customerId: string = '';
  maxPurchaseSize: number = 1;
  retrievalRate: number = 1;
  customerStats: { [key: string]: number } = {}; // Add proper typing
  errorMessage: string = '';

  constructor(private ticketService: TicketService) {}

  ngOnInit() {
    this.updateStats();
    setInterval(() => this.updateStats(), 1000);
  }

  startCustomer() {
    if (this.customerId && this.maxPurchaseSize > 0 && this.retrievalRate > 0) {
      this.ticketService.startCustomer(
        this.customerId,
        this.maxPurchaseSize,
        this.retrievalRate
      ).subscribe({
        next: () => {
          this.errorMessage = '';
          this.updateStats();
        },
        error: (error) => {
          this.errorMessage = error;
        }
      });
    }
  }

  private updateStats() {
    this.ticketService.getCustomerStats()
      .subscribe({
        next: (stats) => {
          this.customerStats = stats;
        },
        error: (error) => {
          this.errorMessage = error;
        }
      });
  }
  stopCustomer(customerId: string) {
    this.ticketService.stopCustomer(customerId)
      .subscribe({
        next: () => {
          this.errorMessage = '';
          this.updateStats();
        },
        error: (error) => {
          this.errorMessage = error;
        }
      });
  }
}
