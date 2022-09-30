// Add your models here if you have any
export interface Order {
    name: string,
    email: string,
    pizzaSize: number,
    base: string,
    sauce: string,
    toppings: string[],
    comments: string
}

export interface OrderDetail {
    orderId: number,
    amount: string
}