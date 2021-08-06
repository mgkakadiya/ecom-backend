package com.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.dao.CartRepository;
import com.ecom.dao.ProductRepository;
import com.ecom.dao.UserRepository;
import com.ecom.entity.Cart;
import com.ecom.entity.Product;
import com.ecom.entity.User;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Override
	public boolean addProductToCart(Integer userId, Integer productId) {
		
		Optional<User> userOp = userRepository.findById(userId);
		User user = null;
		if(userOp.isPresent()) {
			user = userOp.get();
		}else {
			throw new RuntimeException("Did not find user id - "+userId);
		}
		
		Optional<Product> productOp = productRepository.findById(productId);
		Product product = null;
		if(productOp.isPresent()) {
			product = productOp.get();
		}else {
			throw new RuntimeException("Did not find product id - "+productId);
		}
		
		Cart cart = user.getCart();
		List<Product> products = cart.getProducts();
		if(products == null) {
			products = new ArrayList<Product>();
			cart.setProducts(products);
		}
		products.add(product);
		double cartTotal = products.stream().mapToDouble((p)-> p.getPrice()).sum();
		cart.setTotalAmount(cartTotal);
		
		Cart savedCart = cartRepository.save(cart);
		
		return (savedCart != null);
	}

	@Override
	public boolean deleteProductFromCart(Integer userId, Integer productId) {

		Optional<User> userOp = userRepository.findById(userId);
		User user = null;
		if(userOp.isPresent()) {
			user = userOp.get();
		}else {
			throw new RuntimeException("Did not find user id - "+userId);
		}
		
		Optional<Product> productOp = productRepository.findById(productId);
		Product product = null;
		if(productOp.isPresent()) {
			product = productOp.get();
		}else {
			throw new RuntimeException("Did not find product id - "+productId);
		}
		
		boolean isDeleted = false;
		Cart cart = user.getCart();
		List<Product> products = cart.getProducts();
		if(products == null) {
			throw new RuntimeException("Your cart is empty, product can not delete");
		}else {
			isDeleted = products.removeIf((Product p) -> {
				return p.getId() == productId;
			});
		}
		if(isDeleted) {
			double cartTotal = products.stream().mapToDouble((p)-> p.getPrice()).sum();
			cart.setTotalAmount(cartTotal);
		}
		Cart savedCart = cartRepository.save(cart);
		
		return isDeleted;
	}

	@Override
	public Cart getCart(Integer userId) {

		Optional<User> userOp = userRepository.findById(userId);
		User user = null;
		if(userOp.isPresent()) {
			user = userOp.get();
		}else {
			throw new RuntimeException("Did not find user id - "+userId);
		}
		
		Cart cart = user.getCart();
		
		return cart;
	}

}
