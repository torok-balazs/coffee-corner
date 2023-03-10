package com.balazstorok.coffeecorner.domain;

import java.math.BigDecimal;

public interface Product {

	String getName();

	BigDecimal getPrice();

	void setDiscount(BigDecimal discount);

	BigDecimal getDiscountedPrice();

	String toReceiptFormat();
}
