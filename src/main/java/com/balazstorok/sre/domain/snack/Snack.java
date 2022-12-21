package com.balazstorok.sre.domain.snack;

import com.balazstorok.sre.domain.AbstractProduct;
import com.balazstorok.sre.domain.Product;
import java.math.BigDecimal;

public class Snack extends AbstractProduct implements Product {

	public Snack(final String name, final BigDecimal price) {
		super(name, price);
	}
}
