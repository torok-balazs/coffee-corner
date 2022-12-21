package com.balazstorok.coffeecorner;

import com.balazstorok.coffeecorner.domain.Product;
import com.balazstorok.coffeecorner.service.OrderService;
import com.balazstorok.coffeecorner.service.OrderServiceImpl;
import com.balazstorok.coffeecorner.service.ReceiptService;
import com.balazstorok.coffeecorner.service.ReceiptServiceImpl;
import java.util.List;

public class CoffeeCorner {

	private final OrderService orderService;
	private final ReceiptService receiptService;

	public CoffeeCorner() {
		this.orderService = new OrderServiceImpl();
		this.receiptService = new ReceiptServiceImpl(orderService);
	}

	public void order(List<Product> products){
		this.orderService.setProducts(products);
	}

	public void pay() {
		this.orderService.applyDiscount();
		final String receipt = this.receiptService.generate();
		System.out.println(receipt);
	}
}
