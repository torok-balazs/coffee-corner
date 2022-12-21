package com.balazstorok.coffeecorner.service;

import com.balazstorok.coffeecorner.domain.Product;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

	List<Product> getProducts();

	void setProducts(List<Product> products);

	void applyDiscount();

	BigDecimal calculateTotalAmount();
}
