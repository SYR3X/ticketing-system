import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  private handleError(error: HttpErrorResponse) {
    if (error.error && error.error.message) {
      return throwError(() => error.error.message);
    } else if (error.error instanceof ErrorEvent) {
      return throwError(() => error.error.message);
    }
    // Only throw error if there's actually an error
    if (error.status !== 200) {
      return throwError(() => 'Operation failed. Please try again.');
    }
    return throwError(() => ''); // Return empty string for successful operations
  }

  startVendor(vendorId: string, releaseRate: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/vendors?vendorId=${vendorId}&releaseRate=${releaseRate}`, {})
      .pipe(catchError(this.handleError));
  }

  stopVendor(vendorId: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/vendors/${vendorId}`)
      .pipe(catchError(this.handleError));
  }

  startCustomer(customerId: string, maxPurchaseSize: number, retrievalRate: number): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/customers?customerId=${customerId}&maxPurchaseSize=${maxPurchaseSize}&retrievalRate=${retrievalRate}`,
      {}
    ).pipe(catchError(this.handleError));
  }

  stopCustomer(customerId: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/customers/${customerId}`)
      .pipe(catchError(this.handleError));
  }

  getSystemStatus(): Observable<any> {
    return this.http.get(`${this.apiUrl}/system/status`)
      .pipe(catchError(this.handleError));
  }

  getVendorStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/vendors/stats`)
      .pipe(catchError(this.handleError));
  }

  getCustomerStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/customers/stats`)
      .pipe(catchError(this.handleError));
  }
  setTicketCapacity(capacity: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/system/capacity?capacity=${capacity}`, {})
      .pipe(catchError(this.handleError));
  }
  getTransactions(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/transactions`)
      .pipe(catchError(this.handleError));
  }

}

