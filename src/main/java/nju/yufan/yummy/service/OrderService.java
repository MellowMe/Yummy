package nju.yufan.yummy.service;

import nju.yufan.yummy.model.Order;
import nju.yufan.yummy.model.OrderItem;

import java.util.List;

public interface OrderService {
	void insert(Order order);
	int setStatus(int id, int status);
	void insertItems(List<OrderItem> items);
	List<Order> getOrders(int userid);
	List<Order> getOrders4Rest(int restid);
	Order getOrder(int id);
	int delete(int oid);
}
