import { Component, OnInit } from '@angular/core';
import { TicketService } from '../../services/ticket.service';

@Component({
  selector: 'app-ticket-transactions',
  templateUrl: './ticket-transactions.component.html',
  styleUrls: ['./ticket-transactions.component.scss']
})
export class TicketTransactionsComponent implements OnInit {
  transactions: any[] = [];

  constructor(private ticketService: TicketService) {}

  ngOnInit() {
    this.loadTransactions();
    setInterval(() => this.loadTransactions(), 1000);
  }

  private loadTransactions() {
    this.ticketService.getTransactions().subscribe(data => {
      this.transactions = data;
    });
  }
}
