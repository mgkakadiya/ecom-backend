package com.ecom.service;

import com.ecom.entity.Cart;

public interface CartService {

	boolean addProductToCart(Integer userId, Integer productId);
	
	boolean deleteProductFromCart(Integer userId, Integer productId);
	
	Cart getCart(Integer userId);
}
