package com.balazstorok.sre.service;

import com.balazstorok.sre.domain.Product;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

	List<Product> getProducts();

	void setProducts(List<Product> products);

	void applyDiscount();

	BigDecimal calculateTotalAmount();
}
