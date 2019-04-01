package nju.yufan.yummy.service;

import nju.yufan.yummy.dao.AdminDao;
import nju.yufan.yummy.model.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp implements AdminService {
	@Autowired
	private AdminDao adminDao;

	@Override
	public void recharge(double amount) {
		adminDao.recharge(amount);
	}

	@Override
	public void moneyBack(double amount) {
		adminDao.moneyBack(amount);
	}

	@Override
	public Administrator login(String name, String password) {
		return adminDao.login(name, password);
	}

}
