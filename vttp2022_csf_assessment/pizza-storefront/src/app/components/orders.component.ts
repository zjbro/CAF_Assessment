import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { OrderDetail } from '../models';
import { PizzaService } from '../pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit, OnDestroy {

  email = this.ar.snapshot.params['email']
  orderDetails: OrderDetail[] = []
  sub$!: Subscription

  constructor(private pizzaService: PizzaService, private ar: ActivatedRoute) { }

  ngOnInit(): void {
    this.sub$ = this.pizzaService.OnNewOrderDetails.subscribe()
    this.pizzaService.getOrders(this.email)
    .then(result => {
      this.orderDetails = result
    }).catch(error => {
      console.error('>>>Error: ',error)
    })
  }

  ngOnDestroy(): void {
      this.sub$.unsubscribe()
  }

  

}
