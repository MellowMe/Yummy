package nju.yufan.yummy.service;

import nju.yufan.yummy.dao.OrderDao;
import nju.yufan.yummy.dao.OrderItemDao;
import nju.yufan.yummy.model.Order;
import nju.yufan.yummy.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderItemDao orderItemDao;

	@Override
	public void insert(Order order) {
		orderDao.insert(order);
	}

	@Override
	public int setStatus(int id,int status) {
		return orderDao.setStatus(id,status);
	}


	@Override
	public void insertItems(List<OrderItem> items) {
		orderItemDao.insertBatch(items);
	}

	@Override
	public List<Order> getOrders(int userid) {
		List<Order> orders = orderDao.getOrders(userid);
		orders.sort((o1,o2)->-1*Long.compare(o1.getTime().getTime(),o2.getTime().getTime()));
		return orders;
	}

	@Override
	public List<Order> getOrders4Rest(int restid) {
		List<Order> orders = orderDao.getOrders4Rest(restid);
		orders.sort((o1,o2)->-1*Long.compare(o1.getTime().getTime(),o2.getTime().getTime()));
		return orders;
	}

	@Override
	public Order getOrder(int id) {
		return orderDao.getById(id);
	}

	@Override
	@Transactional
	public int delete(int oid) {
		orderItemDao.deleteItems(oid);
		return orderDao.delete(oid);
	}

}
