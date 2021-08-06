package com.ecom.service;

import java.util.List;

import com.ecom.entity.Order;

public interface OrderService {

	Order createOrder(Integer userId);

	Order cancelOrder(Integer orderId);
	
	List<Order> getOrdersByUserId(Integer userId);
}
