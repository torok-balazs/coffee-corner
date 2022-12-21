package com.balazstorok.coffeecorner;

import com.balazstorok.coffeecorner.domain.Product;
import com.balazstorok.coffeecorner.domain.beverage.Beverage;
import com.balazstorok.coffeecorner.domain.beverage.BeverageType;
import com.balazstorok.coffeecorner.domain.beverage.ExtraType;
import com.balazstorok.coffeecorner.domain.snack.Snack;
import com.balazstorok.coffeecorner.domain.snack.SnackType;
import com.balazstorok.coffeecorner.service.OrderServiceImpl;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoffeeCornerTest {

	private static final Logger LOGGER = Logger.getLogger(CoffeeCornerTest.class.getName());

	private final CoffeeCorner coffeeCorner = new CoffeeCorner();
	private final PrintStream systemOutStream = System.out;
	private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(byteArrayOutputStream));
	}

	@AfterEach
	public void tearDown() {
		System.setOut(systemOutStream);
	}

	@Test
	void shouldThrowExceptionWhenNoProductsAreAddedToOrder() {
		final IllegalArgumentException illegalArgumentException = Assertions.assertThrows(
			IllegalArgumentException.class,
			() -> coffeeCorner.order(Collections.emptyList()),
			"Should throw exception");

		Assertions.assertNotNull(illegalArgumentException);
		final String exceptionMessage = illegalArgumentException.getMessage();
		Assertions.assertTrue(exceptionMessage.contains(OrderServiceImpl.EXCEPTION_MESSAGE));
	}

	@Test
	void shouldPrintTotalCHF1475WhenReceiptIsGeneratedWithExampleOrder() {
		final Beverage largeCoffeeWithExtraMilk = BeverageType.COFFEE_L.with(ExtraType.EXTRA_MILK);
		final Beverage smallCoffeeWithSpecialRoast = BeverageType.COFFEE_S.with(ExtraType.SPECIAL_ROAST_COFFEE);
		final Snack baconRoll = SnackType.BACON_ROLL.create();
		final Beverage orangeJuice = BeverageType.ORANGE_JUICE.create();
		final List<Product> products = Arrays.asList(largeCoffeeWithExtraMilk,
			smallCoffeeWithSpecialRoast,
			baconRoll,
			orangeJuice);

		coffeeCorner.order(products);
		coffeeCorner.pay();

		final String actualReceiptContent = byteArrayOutputStream.toString();
		LOGGER.info(actualReceiptContent);
		final NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
		Assertions.assertTrue(actualReceiptContent.contains(numberFormat.format(14.75)));
	}
}
