package nju.yufan.yummy.dao;

import nju.yufan.yummy.model.OrderItem;

import java.util.List;

public interface OrderItemDao {
	void insertBatch(List<OrderItem> items);
	void deleteItems(int oid);
}
