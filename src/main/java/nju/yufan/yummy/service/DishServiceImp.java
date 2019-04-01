package nju.yufan.yummy.service;

import nju.yufan.yummy.dao.DishDao;
import nju.yufan.yummy.dao.OrderDao;
import nju.yufan.yummy.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DishServiceImp implements DishService {
	@Autowired
	private DishDao dishDao;
	@Override
	public List<Dish> getDishes(int restid) {
		return dishDao.getDishes(restid);
	}

	@Override
	public Dish getById(int id) {
		return dishDao.getById(id);
	}

	@Override
	public Map<String, List<Dish>> getSortedDishes(int restid) {
		List<Dish> list = dishDao.getDishes(restid);
		Map<String, List<Dish>> result = new HashMap<>();
		String temp;
		List<Dish> dishes;
		for(Dish dish:list){
			temp = dish.getCate();
			if(result.containsKey(temp))
				result.get(temp).add(dish);
			else{
				dishes = new ArrayList<>();
				dishes.add(dish);
				result.put(temp,dishes);
			}
		}
		return result;
	}

	@Override
	public void setNum(int id, int num) {
		dishDao.setNum(id,num);
	}

	@Override
	public void delete(int id) {
		dishDao.delete(id);
	}

	@Override
	public int insert(Dish dish) {
		return dishDao.insert(dish);
	}

}
