package nju.yufan.yummy.service;

import nju.yufan.yummy.dao.RestaurantDao;
import nju.yufan.yummy.model.Restaurant;
import nju.yufan.yummy.model.RestaurantBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RestaurantServiceImp implements RestaurantService {
	@Autowired
	private RestaurantDao restaurantDao;

	@Override
	public List<Restaurant> getAll() {
		return restaurantDao.getAll();
	}

	@Override
	public List<Restaurant> getByCate(String cate) {
		if (cate.equals("全部"))
			return restaurantDao.getAll();
		else
			return restaurantDao.getByCate(cate);
	}

	@Override
	public Restaurant getById(int id) {
		return restaurantDao.getById(id);
	}

	@Override
	public void receiveMoneyFromUser(int restid, double amount) {
		restaurantDao.receiveMoneyFromUser(restid, amount);
	}

	@Override
	public int moneyBack(int restid, double amount) {
		return restaurantDao.moneyBack(restid, amount);
	}

	@Override
	public void insert(Restaurant restaurant) {
		restaurantDao.insert(restaurant);
	}

	@Override
	public void addModify2back(Restaurant restaurant) {
		restaurantDao.addModify2back(restaurant);
	}

	@Override
	public void updateBack(Restaurant restaurant) {
		restaurantDao.updateBack(restaurant);
	}

	@Override
	public List<RestaurantBack> selectAllBack() {
		List<RestaurantBack> list = restaurantDao.selectAllBack();
		list.sort(Comparator.comparingLong(r ->r.getTime().getTime()));
		return list;
	}

	@Override
	public RestaurantBack selectBack(int id) {
		return restaurantDao.selectBack(id);
	}

	@Override
	public void deleteBack(int id) {
		restaurantDao.deleteBack(id);
	}

	@Override
	public int update(Restaurant restaurant) {
		return restaurantDao.update(restaurant);
	}


}
