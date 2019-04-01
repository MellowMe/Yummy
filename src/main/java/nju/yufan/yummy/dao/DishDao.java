package nju.yufan.yummy.dao;

import nju.yufan.yummy.model.Dish;

import java.util.List;

public interface DishDao {
	List<Dish> getDishes(int restid);
	Dish getById(int id);
	void setNum(int id, int num);
	void delete(int id);
	int insert(Dish dish);
}
