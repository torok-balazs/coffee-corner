package com.balazstorok.coffeecorner.service;

import com.balazstorok.coffeecorner.domain.Product;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReceiptServiceImpl implements ReceiptService {

	protected final static String DIVIDER = "\n----------------------------------------------------";
	protected static final String TOTAL_LABEL = "Total:";
	protected static final String DATE_LABEL = "Date:";
	protected final static String HEADER_LABEL = DIVIDER + "\n------------- Charlene's Coffee Corner -------------" + DIVIDER;
	private final static String DATE_FORMAT = "\n%30.30s\t%20.20s";
	private final static String TOTAL_FORMAT = DIVIDER + DIVIDER + "\n%30.30s\t%20.20s";
	protected final static String FOOTER_LABEL = DIVIDER + "\n------------ Thank you for your order! -------------" + DIVIDER;

	private final OrderService orderService;

	public ReceiptServiceImpl(final OrderService orderService) {
		this.orderService = orderService;
		Locale.setDefault(new Locale("de", "CH"));
	}

	@Override
	public String generate() {
		final StringBuilder stringBuilder = new StringBuilder();

		appendHeader(stringBuilder);
		appendProducts(stringBuilder);
		appendTotal(stringBuilder);
		appendDate(stringBuilder);
		appendFooter(stringBuilder);

		return stringBuilder.toString();
	}

	private void appendHeader(final StringBuilder stringBuilder) {
		stringBuilder.append(HEADER_LABEL);
	}

	private void appendProducts(final StringBuilder stringBuilder) {
		final List<Product> products = orderService.getProducts();
		for (final Product product : products) {
			stringBuilder.append(product.toReceiptFormat());
		}
	}

	private void appendTotal(final StringBuilder stringBuilder) {
		final BigDecimal totalAmount = orderService.calculateTotalAmount();
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
		String formattedTotalAmount = numberFormat.format(totalAmount);
		stringBuilder.append(String.format(TOTAL_FORMAT, TOTAL_LABEL, formattedTotalAmount));
	}

	private void appendDate(final StringBuilder stringBuilder) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		Date now = Calendar.getInstance().getTime();
		stringBuilder.append(String.format(DATE_FORMAT, DATE_LABEL, dateFormat.format(now)));
	}

	private void appendFooter(final StringBuilder stringBuilder) {
		stringBuilder.append(FOOTER_LABEL);
	}
}
