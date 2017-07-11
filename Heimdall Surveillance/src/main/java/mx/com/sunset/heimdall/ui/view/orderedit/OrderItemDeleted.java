package mx.com.sunset.heimdall.ui.view.orderedit;

import mx.com.sunset.heimdall.backend.data.entity.OrderItem;

public class OrderItemDeleted {

	private OrderItem orderItem;

	public OrderItemDeleted(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}
}
