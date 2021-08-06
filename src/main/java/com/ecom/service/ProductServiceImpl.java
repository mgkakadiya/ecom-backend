package com.ecom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.dao.ProductRepository;
import com.ecom.entity.Product;
import com.ecom.rest.ProductNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	
	@Autowired	
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product findById(int theId) {
		Optional<Product> productOp = productRepository.findById(theId);
		
		Product theProduct = null;
		if(productOp.isPresent()) {
			theProduct = productOp.get();
		}else {
			throw new ProductNotFoundException("Did not find product id - "+theId);
		}
		return theProduct;
	}

	@Override
	public void save(Product product) {
		productRepository.save(product);
	}

	@Override
	public void deleteById(int theId) {
		productRepository.deleteById(theId);
	}

}
