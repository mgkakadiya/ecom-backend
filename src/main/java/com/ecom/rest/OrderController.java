package com.ecom.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.entity.Order;
import com.ecom.entity.Product;
import com.ecom.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	// Place order
	@PostMapping("/orders")
	public Order addOrder(@RequestBody Map<String, Integer> createOrderReq) {
		
		Integer userId = createOrderReq.get("userId");
		
		Order order = orderService.createOrder(userId);
		
		return order;
	}
	
	// Get all order
	@GetMapping("/orders/{userId}")
	public List<Order> findAll(@PathVariable int userId){
		return orderService.getOrdersByUserId(userId);
	}
	
	@PutMapping("/orders/{orderId}")
	public Order cancelOrder(@PathVariable int orderId) {
		
		Order order = orderService.cancelOrder(orderId);
		
		return order;
	}
}