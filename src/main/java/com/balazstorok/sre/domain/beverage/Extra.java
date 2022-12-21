package com.balazstorok.sre.domain.beverage;

import com.balazstorok.sre.domain.AbstractProduct;
import com.balazstorok.sre.domain.Product;
import java.math.BigDecimal;

public class Extra extends AbstractProduct implements Product {

	public Extra(final String name, final BigDecimal price) {
		super(name, price);
	}
}
