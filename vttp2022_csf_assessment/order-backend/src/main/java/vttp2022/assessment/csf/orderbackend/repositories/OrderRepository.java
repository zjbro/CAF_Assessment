package vttp2022.assessment.csf.orderbackend.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;

@Repository
public class OrderRepository {

    public static final String SQL_INSERT_ORDER = "insert into orders(name, email, pizza_size, thick_crust, sauce, toppings, comments) values (?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_GET_ORDERS_BY_EMAIL = "select * from orders where email = ?";

    @Autowired
    private JdbcTemplate template;

    public int createOrder(Order order){
        int updated = 0;
        try{
            updated = template.update(
                SQL_INSERT_ORDER, 
                order.getName(), 
                order.getEmail(),
                order.getSize(),
                order.isThickCrust(),
                order.getSauce(),
                order.getToppings().toString(),
                order.getComments()
                );
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return updated;
    }

    public List<Order> getOrdersByEmail(String email){
        System.out.println("Running getOrdersByEmail Repo");
        List<Order> orders = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_GET_ORDERS_BY_EMAIL, email);
        while (rs.next()){
            Order order = Order.create(rs);
            orders.add(order);
        }
        System.out.println("Returned orders from DB: "+ orders);
        return orders;

    }
    
}
