package com.balazstorok.coffeecorner.domain.beverage;

import com.balazstorok.coffeecorner.domain.AbstractProduct;
import com.balazstorok.coffeecorner.domain.Product;
import java.math.BigDecimal;

public class Extra extends AbstractProduct implements Product {

	public Extra(final String name, final BigDecimal price) {
		super(name, price);
	}
}
