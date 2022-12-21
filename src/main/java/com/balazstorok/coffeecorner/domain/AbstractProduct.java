package com.balazstorok.coffeecorner.domain;

import com.balazstorok.coffeecorner.domain.beverage.Extra;
import java.math.BigDecimal;
import java.util.Objects;

public abstract class AbstractProduct implements Product {

	private static final String RECEIPT_FORMAT = "\n%30.30s\t%20.2f";

	private final String name;
	private final BigDecimal price;
	private BigDecimal discount;

	public AbstractProduct(final String name, final BigDecimal price) {
		this.name = name;
		this.price = price;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public void setDiscount(final BigDecimal discount) {
		this.discount = discount;
	}

	@Override
	public BigDecimal getDiscountedPrice() {
		return Objects.isNull(discount) ? price
			: price.subtract(discount);
	}

	@Override
	public String toReceiptFormat() {
		String name = this instanceof Extra ? "+ " + getName() : getName();
		BigDecimal discountedPrice = getDiscountedPrice();
		return String.format(RECEIPT_FORMAT, name, discountedPrice);
	}
}
