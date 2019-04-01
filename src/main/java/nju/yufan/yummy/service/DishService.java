package nju.yufan.yummy.service;

import nju.yufan.yummy.model.Dish;

import java.util.List;
import java.util.Map;

public interface DishService {
	List<Dish> getDishes(int restid);

	Dish getById(int id);

	Map<String, List<Dish>> getSortedDishes(int restid);

	void setNum(int id, int num);

	void delete(int id);

	int insert(Dish dish);

}
