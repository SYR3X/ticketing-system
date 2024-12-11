import { Component, OnInit } from '@angular/core';
import { TicketService } from '../../services/ticket.service';

@Component({
  selector: 'app-vendor-panel',
  templateUrl: './vendor-panel.component.html',
  styleUrls: ['./vendor-panel.component.scss']
})
export class VendorPanelComponent implements OnInit {
  vendorId: string = '';
  releaseRate: number = 1;
  vendorStats: any = {};
  errorMessage: string = '';

  constructor(private ticketService: TicketService) {
  }

  ngOnInit() {
    this.updateStats();
    setInterval(() => this.updateStats(), 1000);
  }

  startVendor() {
    this.errorMessage = '';
    if (this.vendorId && this.releaseRate > 0) {
      this.ticketService.startVendor(this.vendorId, this.releaseRate)
        .subscribe({
          next: () => {
            this.updateStats();
          },
          error: (error) => {
            this.errorMessage = error;
          }
        });
    }
  }

  private updateStats() {
    this.ticketService.getVendorStats()
      .subscribe(stats => {
        this.vendorStats = stats;
      });
  }

  stopVendor(vendorId: string) {
    this.errorMessage = '';
    this.ticketService.stopVendor(vendorId)
      .subscribe({
        next: () => {
          this.updateStats();
        },
        error: (error) => {
          this.errorMessage = error;
        }
      });
  }
}
