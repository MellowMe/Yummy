package nju.yufan.yummy.model;

import java.util.List;

public class Restaurant {
	private int id;
	private String name;
	private String password;
	private String location;
	private String type;
	private String phone;
	private String openhours;
	private String sign;
	private double turnover;
	private List<Dish> dishes;

	public Restaurant(String name, String password, String location, String phone) {
		this.name = name;
		this.password = password;
		this.location = location;
		this.phone = phone;
	}

	public Restaurant(){}

	public Restaurant(int id, String name, String password, String location, String type, String phone, String openhours, String sign) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.location = location;
		this.type = type;
		this.phone = phone;
		this.openhours = openhours;
		this.sign = sign;
	}
	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOpenhours() {
		return openhours;
	}

	public void setOpenhours(String openhours) {
		this.openhours = openhours;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public double getTurnover() {
		return turnover;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() != Restaurant.class)
			return false;
		else {
			Restaurant other = (Restaurant) obj;
			return this.id == other.getId();
		}
	}

	@Override
	public int hashCode() {
		return 31 * id;
	}

}
