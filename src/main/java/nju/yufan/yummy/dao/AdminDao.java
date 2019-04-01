package nju.yufan.yummy.dao;

import nju.yufan.yummy.model.Administrator;

public interface AdminDao {
	void recharge(double amount);
	void moneyBack(double amount);
	Administrator login(String name, String password);
}
