package com.balazstorok.sre.service;

import com.balazstorok.sre.domain.Product;
import com.balazstorok.sre.domain.beverage.Beverage;
import com.balazstorok.sre.domain.beverage.BeverageType;
import com.balazstorok.sre.domain.beverage.ExtraType;
import com.balazstorok.sre.domain.snack.Snack;
import com.balazstorok.sre.domain.snack.SnackType;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReceiptServiceTest {
	private static final Logger LOGGER = Logger.getLogger(ReceiptServiceTest.class.getName());

	private OrderService orderService;
	private ReceiptService receiptService;

	@BeforeEach
	public void setUp() {
		orderService = new OrderServiceImpl();
		receiptService = new ReceiptServiceImpl(orderService);
	}

	@Test
	void shouldContainHeaderWhenReceiptIsGenerated() {
		mockProductsForReceiptGeneration();

		final String receiptContent = receiptService.generate();
		LOGGER.info(receiptContent);

		Assertions.assertTrue(receiptContent.contains(ReceiptServiceImpl.HEADER_LABEL));
	}

	@Test
	void shouldContainTotalLabelWhenReceiptIsGenerated() {
		mockProductsForReceiptGeneration();

		final String receiptContent = receiptService.generate();
		LOGGER.info(receiptContent);

		Assertions.assertTrue(receiptContent.contains(ReceiptServiceImpl.TOTAL_LABEL));
	}

	@Test
	void shouldContainCurrencyWhenReceiptIsGenerated() {
		mockProductsForReceiptGeneration();

		final String receiptContent = receiptService.generate();
		LOGGER.info(receiptContent);

		final Currency currency = NumberFormat.getCurrencyInstance().getCurrency();
		Assertions.assertTrue(receiptContent.contains(currency.getCurrencyCode()));
	}

	@Test
	void shouldContainDateLabelWhenReceiptIsGenerated() {
		mockProductsForReceiptGeneration();

		final String receiptContent = receiptService.generate();
		LOGGER.info(receiptContent);

		Assertions.assertTrue(receiptContent.contains(ReceiptServiceImpl.DATE_LABEL));
	}

	@Test
	void shouldContainFooterLabelWhenReceiptIsGenerated() {
		mockProductsForReceiptGeneration();

		final String receiptContent = receiptService.generate();
		LOGGER.info(receiptContent);

		Assertions.assertTrue(receiptContent.contains(ReceiptServiceImpl.FOOTER_LABEL));
	}

	@Test
	void shouldContain6DividersWhenReceiptIsGenerated() {
		mockProductsForReceiptGeneration();

		final String receiptContent = receiptService.generate();
		LOGGER.info(receiptContent);

		long numberOfDividers = receiptContent.lines().filter(line -> line.contains(ReceiptServiceImpl.DIVIDER.substring(1))).count();
		Assertions.assertEquals(6, numberOfDividers);
	}

	@Test
	void shouldContainAllProductsWhenReceiptIsGenerated() {
		final List<Product> products = mockProductsForReceiptGeneration();

		final String receiptContent = receiptService.generate();
		LOGGER.info(receiptContent);

		products.forEach(product -> Assertions.assertTrue(receiptContent.contains(product.toReceiptFormat())));
	}

	@Test
	void shouldContainAllProductsWithDiscountedPriceWhenReceiptIsGenerated() {
		final List<Product> products = mockProductsForReceiptGeneration();
		orderService.applyDiscount();

		final String receiptContent = receiptService.generate();
		LOGGER.info(receiptContent);

		products.forEach(product -> Assertions.assertTrue(receiptContent.contains(product.toReceiptFormat())));
	}

	private List<Product> mockProductsForReceiptGeneration() {
		final Snack snack1 = SnackType.BACON_ROLL.create();
		final Beverage smallCoffee = BeverageType.COFFEE_S.create();
		final Beverage mediumCoffeeWithExtraMilkAndSpecialRoast = BeverageType.COFFEE_M.with(ExtraType.EXTRA_MILK);
		mediumCoffeeWithExtraMilkAndSpecialRoast.addExtra(ExtraType.SPECIAL_ROAST_COFFEE.create());
		final Beverage largeCoffeeWithFoamedMilk = BeverageType.COFFEE_L.with(ExtraType.FOAMED_MILK);
		final Beverage orangeJuice = BeverageType.ORANGE_JUICE.create();
		final Snack snack2 = SnackType.BACON_ROLL.create();

		final List<Product> productsToOrder = new ArrayList<>();
		productsToOrder.add(snack1);
		productsToOrder.add(smallCoffee);
		productsToOrder.add(mediumCoffeeWithExtraMilkAndSpecialRoast);
		productsToOrder.add(largeCoffeeWithFoamedMilk);
		productsToOrder.add(orangeJuice);
		productsToOrder.add(snack2);

		orderService.setProducts(productsToOrder);

		return productsToOrder;
	}
}
