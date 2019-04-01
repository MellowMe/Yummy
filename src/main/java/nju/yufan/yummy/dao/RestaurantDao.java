package nju.yufan.yummy.dao;

import nju.yufan.yummy.model.Restaurant;
import nju.yufan.yummy.model.RestaurantBack;

import java.util.List;

public interface RestaurantDao {
	List<Restaurant> getAll();
	List<Restaurant> getByCate(String cate);
	Restaurant getById(int id);
	int receiveMoneyFromUser(int restid, double amount);
	int moneyBack(int restid, double amount);
	void insert(Restaurant restaurant);
	void addModify2back(Restaurant restaurant);
	void updateBack(Restaurant restaurant);
	List<RestaurantBack> selectAllBack();
	RestaurantBack selectBack(int id);
	void deleteBack(int id);
	int update(Restaurant restaurant);
}
