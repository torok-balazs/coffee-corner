package com.balazstorok.sre;

import com.balazstorok.sre.domain.Product;
import com.balazstorok.sre.service.OrderService;
import com.balazstorok.sre.service.OrderServiceImpl;
import com.balazstorok.sre.service.ReceiptService;
import com.balazstorok.sre.service.ReceiptServiceImpl;
import java.util.List;

public class Application {

	private final OrderService orderService;
	private final ReceiptService receiptService;

	public Application() {
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
