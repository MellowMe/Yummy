package nju.yufan.yummy.service;

import nju.yufan.yummy.model.Administrator;

public interface AdminService {
	void recharge(double amount);
	void moneyBack(double amount);
	Administrator login(String name, String password);
}
