package nju.yufan.yummy.model;

public class OrderItem {
	private int id;
	private int orderid;
	private int dishid;
	private int number;
	private double price;
	private Dish dish;

	public OrderItem(int orderid, int dishid, int number, double price) {
		this.orderid = orderid;
		this.dishid = dishid;
		this.number = number;
		this.price = price;
	}

	public OrderItem(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getDishid() {
		return dishid;
	}

	public void setDishid(int dishid) {
		this.dishid = dishid;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
