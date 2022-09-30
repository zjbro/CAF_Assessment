import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Order } from '../models';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  form!: FormGroup

  pizzaSize = SIZES[0]
  pizzaToppingsArray: string[] = []
 

  constructor(private fb: FormBuilder, private router: Router, private pizzaService: PizzaService) {}

  ngOnInit(): void {
    this.form = this.createForm()

  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  createForm(){
    return this.fb.group({
      name: this.fb.control<string>('', [ Validators.required ]),
      email: this.fb.control<string>('', [ Validators.required, Validators.email ]),
      pizzaSize: this.fb.control<number>(0, [Validators.required ]),
      base: this.fb.control<string>('', [ Validators.required ]),
      sauce: this.fb.control<string>('', [ Validators.required ]),
      comments: this.fb.control<string>('', [ Validators.maxLength(300) ]),    
    })
  }

  submitOrder(){
    let order: Order = this.form.value as Order
    order.toppings = this.pizzaToppingsArray
		console.info('>>>>order created: ', order)
    this.pizzaService.createOrder(order)
    this.pizzaService.OnNewOrderDetails.next(order.email)
    this.router.navigate(['orders', order.email])
    
  }

  getOrders(){
    let order: Order = this.form.value as Order
		console.info('>>>>email input: ', order.email)
    this.pizzaService.getOrders(order.email)
    this.router.navigate(['orders', order.email])
  }
  

  addChicken(){
     this.pizzaToppingsArray.push('chicken')
     console.info('pizza toppings array: ',this.pizzaToppingsArray) 
  }

  addSeafood(){
    this.pizzaToppingsArray.push('seafood') 
    console.info('pizza toppings array: ',this.pizzaToppingsArray) 
 }

  addBeef(){
    this.pizzaToppingsArray.push('beef') 
    console.info('pizza toppings array: ',this.pizzaToppingsArray) 
  }

  addVegetables(){
    this.pizzaToppingsArray.push('vegetables') 
    console.info('pizza toppings array: ',this.pizzaToppingsArray) 
  }

  addCheese(){
    this.pizzaToppingsArray.push('cheese') 
    console.info('pizza toppings array: ',this.pizzaToppingsArray) 
  }

  addArugula(){
    this.pizzaToppingsArray.push('arugula') 
    console.info('pizza toppings array: ',this.pizzaToppingsArray) 
  }

  addPineapple(){
    this.pizzaToppingsArray.push('pineapple') 
    console.info('pizza toppings array: ',this.pizzaToppingsArray) 
  }

  


}

