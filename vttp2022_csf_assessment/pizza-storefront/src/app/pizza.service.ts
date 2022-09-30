// Implement the methods in PizzaService for Task 3
// Add appropriate parameter and return type 

import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom, Subject } from "rxjs";
import { Order, OrderDetail } from "./models";

@Injectable()
export class PizzaService {

  OnNewOrderDetails = new Subject<String>()

  constructor(private http: HttpClient) { }

  // POST /api/order
  // Add any required parameters or return type
  createOrder(order: Order) {
    return firstValueFrom(
      this.http.post<any>('/api/order', order)
    )
  }

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(email: string): Promise<OrderDetail[]> { 
    return firstValueFrom(
      this.http.get<OrderDetail[]>(`/api/order/${email}/all`)
    )
    

  }

}
