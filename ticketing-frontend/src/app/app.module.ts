import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { TicketDashboardComponent } from './components/ticket-dashboard/ticket-dashboard.component';
import { VendorPanelComponent } from './components/vendor-panel/vendor-panel.component';
import { CustomerPanelComponent } from './components/customer-panel/customer-panel.component';
import { TicketTransactionsComponent } from './components/ticket-transactions/ticket-transactions.component';
import { TicketService } from './services/ticket.service';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: TicketDashboardComponent },
  { path: 'vendors', component: VendorPanelComponent },
  { path: 'customers', component: CustomerPanelComponent },
  { path: 'transactions', component: TicketTransactionsComponent }
];


@NgModule({
  declarations: [
    AppComponent,
    TicketDashboardComponent,
    VendorPanelComponent,
    CustomerPanelComponent,
    TicketTransactionsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [TicketService],
  bootstrap: [AppComponent]
})
export class AppModule { }
