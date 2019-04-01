package nju.yufan.yummy.service;

import nju.yufan.yummy.dao.UserDao;
import nju.yufan.yummy.model.Token;
import nju.yufan.yummy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	UserDao userDao;

	@Override
	public User login(String email, String password) {
		return userDao.login(email, password);
	}

	@Override
	public int insert(User user) {
		return userDao.insert(user);
	}

	@Override
	public int getId(String email) {
		return userDao.getId(email);
	}

	@Override
	public Token getToken(int id) {
		return userDao.getToken(id);
	}

	@Override
	public void activate(int id) {
		userDao.activate(id);
	}

	@Override
	public int pay(int id, double amount) {
		return userDao.pay(id, amount);
	}

	@Override
	public int recharge(int id, double amount) {
		return userDao.recharge(id,amount);
	}

	@Override
	public int logoff(int id) {
		return userDao.logoff(id);
	}

	@Override
	public int update(User user) {
		return userDao.update(user);
	}

}
