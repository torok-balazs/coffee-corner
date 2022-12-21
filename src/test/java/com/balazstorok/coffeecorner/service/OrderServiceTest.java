package com.balazstorok.coffeecorner.service;

import com.balazstorok.coffeecorner.domain.Product;
import com.balazstorok.coffeecorner.domain.beverage.Beverage;
import com.balazstorok.coffeecorner.domain.beverage.BeverageType;
import com.balazstorok.coffeecorner.domain.beverage.ExtraType;
import com.balazstorok.coffeecorner.domain.snack.SnackType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
	private OrderService orderService;

	@BeforeEach
	public void setUp() {
		orderService = new OrderServiceImpl();
	}


	@Test
	void shouldGetProductsAsEmptyList() {
		final List<Product> products = orderService.getProducts();
		Assertions.assertTrue(products.isEmpty());
	}

	@Test
	void shouldGetProducts() {
		final List<Product> mockedBeverages = mockBeverages(3);
		final List<Product> mockedBeveragesWith1Extra = mockBeveragesWith1Extra(2);
		final List<Product> mockedBeveragesWithAllExtras = mockBeveragesWithAllExtras(4);
		final List<Product> mockedSnacks = mockSnacks(6);

		final List<Product> products = orderService.getProducts();

		assertProductsContainsAll(products, mockedBeverages);
		assertProductsContainsAll(products, mockedBeveragesWith1Extra);
		assertProductsContainsAll(products, mockedBeveragesWithAllExtras);
		assertProductsContainsAll(products, mockedSnacks);
	}

	@Test
	void shouldThrowExceptionWhenSetProductsWithNull() {
		final IllegalArgumentException illegalArgumentException = Assertions.assertThrows(
			IllegalArgumentException.class,
			() -> orderService.setProducts(null),
			"Should throw exception");

		Assertions.assertNotNull(illegalArgumentException);
		final String exceptionMessage = illegalArgumentException.getMessage();
		Assertions.assertTrue(exceptionMessage.contains(OrderServiceImpl.EXCEPTION_MESSAGE));
	}

	@Test
	void shouldThrowExceptionWhenSetProductsWithEmptyList() {
		final IllegalArgumentException illegalArgumentException = Assertions.assertThrows(
			IllegalArgumentException.class,
			() -> orderService.setProducts(Collections.emptyList()),
			"Should throw exception");

		Assertions.assertNotNull(illegalArgumentException);
		final String exceptionMessage = illegalArgumentException.getMessage();
		Assertions.assertTrue(exceptionMessage.contains(OrderServiceImpl.EXCEPTION_MESSAGE));
	}

	@Test
	void shouldApply1StampCardDiscountWhen5BeverageIsOrdered() {
		mockBeverages(5);

		orderService.applyDiscount();

		assertDiscountedBeverages(1);

	}

	@Test
	void shouldApply2StampCardDiscountWhen10BeverageIsOrdered() {
		mockBeverages(10);

		orderService.applyDiscount();

		assertDiscountedBeverages(2);
	}

	@Test
	void shouldNotApplyAnyStampCardDiscount() {
		mockBeverages(4);

		orderService.applyDiscount();

		assertDiscountedBeverages(0);
	}

	@Test
	void shouldApply1ExtraDiscountWhen1SnackIsOrdered() {
		mockBeverages(1);
		mockBeveragesWith1Extra(2);
		mockBeveragesWithAllExtras(3);
		mockSnacks(1);

		orderService.applyDiscount();

		assertDiscountedExtras(1);
	}

	@Test
	void shouldApply2ExtraDiscountWhen2SnackIsOrdered() {
		mockBeverages(4);
		mockBeveragesWith1Extra(2);
		mockBeveragesWithAllExtras(1);
		mockSnacks(2);

		orderService.applyDiscount();

		assertDiscountedExtras(2);
	}

	@Test
	void shouldApplyOnly1ExtraDiscountWhenOnly1BeverageAnd2SnackIsOrdered() {
		mockBeveragesWithAllExtras(1);
		mockSnacks(2);

		orderService.applyDiscount();

		assertDiscountedExtras(1);
	}

	@Test
	void shouldApplyOnly2ExtraDiscountWhenOnly2BeverageAnd5SnackIsOrdered() {
		mockBeveragesWithAllExtras(2);
		mockSnacks(5);

		orderService.applyDiscount();

		assertDiscountedExtras(2);
	}

	@Test
	void shouldNotApplyExtraDiscountWhenNoSnackIsOrdered() {
		mockBeverages(2);
		mockBeveragesWith1Extra(3);
		mockBeveragesWithAllExtras(4);

		orderService.applyDiscount();

		assertDiscountedExtras(0);
	}

	@Test
	void shouldNotApplyExtraDiscountWhenNoExtraIsOrdered() {
		mockBeverages(2);
		mockSnacks(2);

		orderService.applyDiscount();

		assertDiscountedExtras(0);
	}

	@Test
	void shouldCalculateTotalAmountWithoutDiscount() {
		mockProductsForTotalCountCalculation();

		final BigDecimal totalAmount = orderService.calculateTotalAmount();

		Assertions.assertEquals(new BigDecimal("28.15"), totalAmount);
	}

	@Test
	void shouldCalculateTotalAmountWithDiscount() {
		mockProductsForTotalCountCalculation();

		orderService.applyDiscount();
		final BigDecimal totalAmount = orderService.calculateTotalAmount();

		Assertions.assertEquals(new BigDecimal("26.45"), totalAmount);
	}

	private List<Product> mockBeverages(final int count) {
		final List<Product> productsToOrder = new ArrayList<>();
		final BeverageType[] beverageTypes = BeverageType.values();
		IntStream.range(0, count).forEach(i -> {
			final int randomBeverageIndex = (int) (Math.random() * beverageTypes.length);
			productsToOrder.add(beverageTypes[randomBeverageIndex].create());
		});

		final List<Product> products = orderService.getProducts();
		products.addAll(productsToOrder);

		return productsToOrder;
	}

	private List<Product> mockBeveragesWith1Extra(final int count) {
		final List<Product> productsToOrder = new ArrayList<>();
		final BeverageType[] beverageTypes = BeverageType.values();
		final ExtraType[] extraTypes = ExtraType.values();
		IntStream.range(0, count).forEach(i -> {
			int randomBeverageIndex = (int) (Math.random() * beverageTypes.length);
			int randomExtraIndex = (int) (Math.random() * extraTypes.length);
			productsToOrder.add(
				beverageTypes[randomBeverageIndex].with(extraTypes[randomExtraIndex]));
		});

		final List<Product> products = orderService.getProducts();
		products.addAll(productsToOrder);

		return productsToOrder;
	}

	private List<Product> mockBeveragesWithAllExtras(final int count) {
		final List<Product> productsToOrder = new ArrayList<>();
		final BeverageType[] beverageTypes = BeverageType.values();
		IntStream.range(0, count).forEach(i -> {
			final int randomBeverageIndex = (int) (Math.random() * beverageTypes.length);
			final Beverage beverage = beverageTypes[randomBeverageIndex].create();
			beverage.addExtra(ExtraType.EXTRA_MILK.create());
			beverage.addExtra(ExtraType.FOAMED_MILK.create());
			beverage.addExtra(ExtraType.SPECIAL_ROAST_COFFEE.create());
			productsToOrder.add(beverage);
		});

		final List<Product> products = orderService.getProducts();
		products.addAll(productsToOrder);

		return productsToOrder;
	}

	private List<Product> mockSnacks(final int count) {
		final List<Product> productsToOrder = new ArrayList<>();
		final SnackType[] snackTypes = SnackType.values();
		IntStream.range(0, count).forEach(i -> {
			final int randomSnackIndex = (int) (Math.random() * snackTypes.length);
			productsToOrder.add(snackTypes[randomSnackIndex].create());
		});

		final List<Product> products = orderService.getProducts();
		products.addAll(productsToOrder);

		return productsToOrder;
	}

	private void assertProductsContainsAll(final List<Product> actualProducts,
		final List<Product> expectedProducts) {
		Assertions.assertNotNull(actualProducts);
		Assertions.assertFalse(actualProducts.isEmpty());
		Assertions.assertTrue(actualProducts.containsAll(expectedProducts));
	}

	private void assertDiscountedBeverages(int count) {
		final List<Product> products = orderService.getProducts();
		long discountedBeverages = products.stream()
			.filter(product -> product instanceof Beverage)
			.filter(product -> BigDecimal.ZERO.compareTo(product.getDiscountedPrice()) == 0)
			.count();
		Assertions.assertEquals(count, discountedBeverages);
	}

	private void assertDiscountedExtras(final int count) {
		final List<Product> products = orderService.getProducts();
		final long discountedExtras = products.stream()
			.filter(product -> product instanceof Beverage)
			.map(product -> (Beverage) product)
			.flatMap(beverage -> beverage.getExtras().stream())
			.filter(extra -> BigDecimal.ZERO.compareTo(extra.getDiscountedPrice()) == 0)
			.count();
		Assertions.assertEquals(count, discountedExtras);
	}

	private void mockProductsForTotalCountCalculation() {
		mockSnacks(3);

		final Beverage smallCoffee = BeverageType.COFFEE_S.create();
		final Beverage mediumCoffeeWithExtraMilkAndSpecialRoast = BeverageType.COFFEE_M.with(ExtraType.EXTRA_MILK);
		mediumCoffeeWithExtraMilkAndSpecialRoast.addExtra(ExtraType.SPECIAL_ROAST_COFFEE.create());
		final Beverage largeCoffeeWithFoamedMilk = BeverageType.COFFEE_L.with(ExtraType.FOAMED_MILK);
		final Beverage orangeJuice = BeverageType.ORANGE_JUICE.create();

		final List<Product> productsToOrder = orderService.getProducts();
		productsToOrder.add(smallCoffee);
		productsToOrder.add(mediumCoffeeWithExtraMilkAndSpecialRoast);
		productsToOrder.add(largeCoffeeWithFoamedMilk);
		productsToOrder.add(orangeJuice);
	}
}
