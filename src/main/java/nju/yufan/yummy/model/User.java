package nju.yufan.yummy.model;

import java.sql.Timestamp;
import java.util.List;

public class User{
	private int id;
	private String email;
	private String username;
	private String password;
	private String phone;
	private int level;
	private double balance;
	private double spend;
	private boolean alive;
	private String token;
	private Timestamp tokenExpireTime;
	private List<Address> addresses;

	public User(String email, String username, String password, String phone) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", phone='" + phone + '\'' +
				", level=" + level +
				", balance=" + balance +
				", spend=" + spend +
				", alive=" + alive +
				", token='" + token + '\'' +
				", tokenExpireTime=" + tokenExpireTime +
				", addresses=" + addresses +
				'}';
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getSpend() {
		return spend;
	}

	public void setSpend(double spend) {
		this.spend = spend;
	}

	public Timestamp getTokenExpireTime() {
		return tokenExpireTime;
	}

	public void setTokenExpireTime(Timestamp tokenExpireTime) {
		this.tokenExpireTime = tokenExpireTime;
	}
}
