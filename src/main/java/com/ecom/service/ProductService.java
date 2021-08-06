package com.ecom.service;

import java.util.List;

import com.ecom.entity.Product;

public interface ProductService {

	public List<Product> findAll();
	
	public Product findById(int theId);
	
	public void save(Product product);
	
	public void deleteById(int theId);
}
