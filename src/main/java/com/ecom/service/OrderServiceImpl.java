package com.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.dao.OrderRepository;
import com.ecom.dao.UserRepository;
import com.ecom.entity.Cart;
import com.ecom.entity.Order;
import com.ecom.entity.OrderState;
import com.ecom.entity.Product;
import com.ecom.entity.User;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public Order createOrder(Integer userId) {
		
		Optional<User> userOp = userRepository.findById(userId);
		User user = null;
		if(userOp.isPresent()) {
			user = userOp.get();
		}else {
			throw new RuntimeException("Did not find user id - "+userId);
		}
		
		Cart cart = user.getCart();
		List<Product> products = cart.getProducts();
		if(products == null) {
			throw new RuntimeException("Your cart is empty, you can not place order");
		}

		
		Order order = new Order();
		order.setUser(user);
		order.setTotalAmount(cart.getTotalAmount());
		
		boolean isPaymentSuccess = makePayment(order.getTotalAmount());
		if(isPaymentSuccess) {
			order.setOrderState(OrderState.COMPLETED);
		}else {
			order.setOrderState(OrderState.PENDING);
		}
		
		List<Product> orderItems = new ArrayList<Product>();
		orderItems.addAll(products);
		order.setProducts(orderItems);
		Order savedOrder = orderRepository.save(order);
		
		cart.getProducts().clear();
		
		return savedOrder;
	}

	boolean makePayment(Double orderAmount){
		// Need to integrate third party Api for payment like Stripe or other payment gateway
		// Currently assumed that payment will be success
		return true;
	}

	@Override
	public List<Order> getOrdersByUserId(Integer userId) {
		return orderRepository.findByUserId(userId);
	}

	@Override
	public Order cancelOrder(Integer orderId) {
		
		Optional<Order> orderOp = orderRepository.findById(orderId);
		Order order = null;
		if(orderOp.isPresent()) {
			order = orderOp.get();
		}else {
			throw new RuntimeException("Did not find order id - "+orderId);
		}
		
		order.setOrderState(OrderState.CANCELLED);
		
		orderRepository.save(order);
		
		return order;
	}
}
