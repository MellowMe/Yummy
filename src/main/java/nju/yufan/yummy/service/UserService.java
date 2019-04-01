package nju.yufan.yummy.service;

import nju.yufan.yummy.model.Token;
import nju.yufan.yummy.model.User;

public interface UserService {
	User login(String email, String password);
	int insert(User user);
	int getId(String email);
	Token getToken(int id);
	void activate(int id);
	int pay(int id, double amount);
	int recharge(int id, double amount);
	int logoff(int id);
	int update(User user);
}
