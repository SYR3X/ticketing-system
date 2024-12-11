import { Component, OnInit, OnDestroy } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { interval, Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';

interface SystemStatus {
  availableTickets: number;
  maxCapacity: number;
  vendorStats: { [key: string]: number };
  customerStats: { [key: string]: number };
}


@Component({
  selector: 'app-ticket-dashboard',
  templateUrl: './ticket-dashboard.component.html',
  styleUrls: ['./ticket-dashboard.component.scss']
})
export class TicketDashboardComponent implements OnInit, OnDestroy {
  systemStatus: SystemStatus = {
    availableTickets: 0,
    vendorStats: {},
    customerStats: {},
    maxCapacity: 0
  };

  capacity: number = 1000;
  errorMessage: string = '';
  private statusSubscription: Subscription;

  constructor(private ticketService: TicketService) {}

  ngOnInit() {
    this.updateStatus();
    this.statusSubscription = interval(1000)
      .pipe(
        switchMap(() => this.ticketService.getSystemStatus())
      )
      .subscribe({
        next: (status: SystemStatus) => {
          this.systemStatus = status;
        },
        error: (error) => {
          this.errorMessage = error;
        }
      });
  }

  ngOnDestroy() {
    if (this.statusSubscription) {
      this.statusSubscription.unsubscribe();
    }
  }

  updateCapacity() {
    if (this.capacity > 0) {
      this.ticketService.setTicketCapacity(this.capacity)
        .subscribe({
          next: () => {
            this.errorMessage = '';
            this.updateStatus();
          },
          error: (error) => {
            this.errorMessage = error;
          }
        });
    } else {
      this.errorMessage = 'Capacity must be greater than 0';
    }
  }

  private updateStatus() {
    this.ticketService.getSystemStatus()
      .subscribe({
        next: (status: SystemStatus) => {
          this.systemStatus = status;
        },
        error: (error) => {
          this.errorMessage = error;
        }
      });
  }

  getTotalTicketsReleased(): number {
    return Object.values(this.systemStatus.vendorStats)
      .reduce((sum: number, current: number) => sum + current, 0);
  }

  getTotalTicketsPurchased(): number {
    return Object.values(this.systemStatus.customerStats)
      .reduce((sum: number, current: number) => sum + current, 0);
  }
}
