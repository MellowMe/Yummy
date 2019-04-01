package nju.yufan.yummy.dao;

import nju.yufan.yummy.model.Order;

import java.util.List;

public interface OrderDao {
	List<Order> getOrders(int userid);
	List<Order> getOrders4Rest(int restid);
	void insert(Order order);
	int setStatus(int id,int status);
	Order getById(int oid);
	int delete(int id);
}
