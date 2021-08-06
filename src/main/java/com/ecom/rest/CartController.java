package com.ecom.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.entity.Cart;
import com.ecom.service.CartService;

@RestController
@RequestMapping("/api")
public class CartController {

	private CartService cartService;

	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@PostMapping("/carts/products")
	public boolean addProduct(@RequestBody Map<String, Integer> addProductToCartReq) {
		
		Integer userId = addProductToCartReq.get("userId");
		Integer productId = addProductToCartReq.get("productId");
		
		boolean isAdded = cartService.addProductToCart(userId, productId);
		
		return isAdded;
	}
	
	@DeleteMapping("/carts/products")
	public boolean deleteProduct(@RequestBody Map<String, Integer> deleteProductToCartReq){
		Integer userId = deleteProductToCartReq.get("userId");
		Integer productId = deleteProductToCartReq.get("productId");
		
		boolean isDeleted = cartService.deleteProductFromCart(userId, productId);
		
		return isDeleted;
	}
	
	@GetMapping("/carts/{userId}")
	public Cart getCart(@PathVariable int userId){
		Cart cart = cartService.getCart(userId);
		
		if(cart == null) {
			throw new RuntimeException("Cart detail not found for user id - "+userId);
		}
		
		return cart;
	}
}
