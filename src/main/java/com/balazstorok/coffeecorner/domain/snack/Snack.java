package com.balazstorok.coffeecorner.domain.snack;

import com.balazstorok.coffeecorner.domain.AbstractProduct;
import com.balazstorok.coffeecorner.domain.Product;
import java.math.BigDecimal;

public class Snack extends AbstractProduct implements Product {

	public Snack(final String name, final BigDecimal price) {
		super(name, price);
	}
}
