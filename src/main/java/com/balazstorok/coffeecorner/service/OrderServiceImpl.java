package com.balazstorok.coffeecorner.service;

import com.balazstorok.coffeecorner.domain.AbstractProduct;
import com.balazstorok.coffeecorner.domain.Product;
import com.balazstorok.coffeecorner.domain.beverage.Beverage;
import com.balazstorok.coffeecorner.domain.beverage.Extra;
import com.balazstorok.coffeecorner.domain.snack.Snack;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

	public static final String EXCEPTION_MESSAGE = "products must be set and have at least one element";
	private List<Product> products = new ArrayList<>();

	@Override
	public List<Product> getProducts() {
		return this.products;
	}

	@Override
	public void setProducts(final List<Product> products) {
		if (products == null || products.isEmpty()) {
			throw new IllegalArgumentException(
				EXCEPTION_MESSAGE);
		}
		this.products = products;
	}

	@Override
	public void applyDiscount() {
		applyStampCardDiscount();
		applyExtraDiscount();
	}

	@Override
	public BigDecimal calculateTotalAmount() {
		final BigDecimal beveragesTotalAmount = getBeverages()
			.stream()
			.map(AbstractProduct::getDiscountedPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		final BigDecimal snacksTotalAmount = getSnacks()
			.stream()
			.map(AbstractProduct::getDiscountedPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		final BigDecimal extrasTotalAmount = getExtrasSortedByPriceDescending()
			.stream()
			.map(AbstractProduct::getDiscountedPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		return beveragesTotalAmount.add(snacksTotalAmount).add(extrasTotalAmount);
	}

	private List<Beverage> getBeverages() {
		return products
			.stream()
			.filter(product -> product instanceof Beverage)
			.map(product -> (Beverage) product)
			.collect(Collectors.toList());
	}

	private List<Snack> getSnacks() {
		return products
			.stream()
			.filter(product -> product instanceof Snack)
			.map(product -> (Snack) product)
			.collect(Collectors.toList());
	}

	private List<Extra> getExtrasSortedByPriceDescending() {
		return getBeverages()
			.stream()
			.flatMap(beverage -> beverage.getExtras().stream())
			.filter(Objects::nonNull)
			.sorted(Comparator.comparing(AbstractProduct::getPrice, Comparator.reverseOrder()))
			.collect(Collectors.toList());
	}

	private void applyStampCardDiscount() {
		final List<Beverage> beverages = getBeverages();

		int count = 0;
		for (Beverage beverage : beverages) {
			if (count == 4) {
				beverage.setDiscount(beverage.getPrice());
				count = 0;
			} else {
				count++;
			}
		}
	}

	public void applyExtraDiscount() {
		final List<Beverage> beverages = getBeverages();
		final List<Snack> snacks = getSnacks();
		final int maxNumberOfExtraDiscount = Math.min(beverages.size(), snacks.size());

		if (maxNumberOfExtraDiscount > 0) {
			getExtrasSortedByPriceDescending()
				.stream()
				.limit(maxNumberOfExtraDiscount)
				.forEach(extra -> extra.setDiscount(extra.getPrice()));
		}
	}
}
