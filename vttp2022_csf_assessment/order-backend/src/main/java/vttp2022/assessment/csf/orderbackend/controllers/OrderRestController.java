package vttp2022.assessment.csf.orderbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.services.OrderService;

@RestController
@RequestMapping(path="/api", produces=MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    @Autowired
    private OrderService orderSvc;

    @PostMapping(path="/order", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrder(@RequestBody String payload){ 
        Order o = Order.create(payload);
        orderSvc.createOrder(o);

        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/order/{email}/all")
    public ResponseEntity<String> getOrderSummary (@PathVariable("email") String email){
        List<OrderSummary> orderSummaries = orderSvc.getOrdersByEmail(email);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (OrderSummary orderSummary: orderSummaries)
            arrBuilder.add(orderSummary.toJson());

        return ResponseEntity.ok(arrBuilder.build().toString());
    }

}
