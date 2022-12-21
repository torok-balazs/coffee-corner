package com.balazstorok.coffeecorner.domain.beverage;

import com.balazstorok.coffeecorner.domain.AbstractProduct;
import com.balazstorok.coffeecorner.domain.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Beverage extends AbstractProduct implements Product {

	private final List<Extra> extras;

	public Beverage(final String name, final BigDecimal price) {
		super(name, price);
		this.extras = new ArrayList<>();
	}

	public List<Extra> getExtras() {
		return extras;
	}

	public void addExtra(final Extra extra) {
		Objects.requireNonNull(extra);
		extras.add(extra);
	}

	@Override
	public String toReceiptFormat() {
		if (extras.isEmpty()) {
			return super.toReceiptFormat();
		} else {
			final StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(super.toReceiptFormat());
			extras.forEach(extra -> stringBuilder.append(extra.toReceiptFormat()));
			stringBuilder.append("\n");
			return stringBuilder.toString();
		}
	}
}
