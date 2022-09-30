package vttp2022.assessment.csf.orderbackend.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private PricingService priceSvc;

	@Autowired
	private OrderRepository orderRepo;

	// POST /api/order
	// Create a new order by inserting into orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public void createOrder(Order order) {
		orderRepo.createOrder(order);
		return;

	}

	// GET /api/order/<email>/all
	// Get a list of orders for email from orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public List<OrderSummary> getOrdersByEmail(String email) {
		List<OrderSummary> orderSummaries = new LinkedList<>();
		List<Order> orders = orderRepo.getOrdersByEmail(email);

		// Use priceSvc to calculate the total cost of an order
		for (int i = 0; i < orders.size(); i ++){
			Float totalAmount = 0f;
			OrderSummary orderSummary = new OrderSummary();
			orderSummary.setOrderId(orders.get(i).getOrderId());
			orderSummary.setName(orders.get(i).getName());
			orderSummary.setEmail(orders.get(i).getEmail());
			
			Float sauceCost = priceSvc.sauce(orders.get(i).getSauce());
			Float sizeCost = priceSvc.size(orders.get(i).getSize());
			Float crustCost = 0f;
			if(orders.get(i).isThickCrust()){
				crustCost = priceSvc.thickCrust();
			}
			else {
				crustCost = priceSvc.thinCrust();
			}
			Float toppingCost = 0f;
			for (int j = 0; j < orders.get(i).getToppings().size(); j++) {
				System.out.println("toppings in orders>>>>"+orders.get(i).getToppings().get(j));
				toppingCost += priceSvc.topping(orders.get(i).getToppings().get(j).toString());
			}
			System.out.println("Sauce Cost: " + sauceCost);
			System.out.println("Crust Cost: " + crustCost);
			System.out.println("Size Cost: " + sizeCost);
			System.out.println("Topping Cost: " + toppingCost);
			totalAmount = sauceCost + crustCost + sizeCost + toppingCost;
			orderSummary.setAmount(totalAmount);
			orderSummaries.add(orderSummary);
		}
		System.out.println("Returned Order Summaries from Service: " + orderSummaries);
		return orderSummaries;
	}
}
