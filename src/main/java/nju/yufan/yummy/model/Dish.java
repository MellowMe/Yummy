package nju.yufan.yummy.model;

public class Dish {
	private int id;
	private int restid;
	private String name;
	private String cate;
	private double price;
	private String picture;
	private int num;
	private boolean available;

	public Dish(int restid, String name, String cate, double price, String picture) {
		this.restid = restid;
		this.name = name;
		this.cate = cate;
		this.price = price;
		this.picture = picture;
	}

	public Dish() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRestid() {
		return restid;
	}

	public void setRestid(int restid) {
		this.restid = restid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() != Dish.class)
			return false;
		else {
			Dish other = (Dish) obj;
			return this.id == other.getId();
		}
	}

	@Override
	public int hashCode() {
		return 31 * id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
