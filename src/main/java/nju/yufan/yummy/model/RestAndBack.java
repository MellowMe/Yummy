package nju.yufan.yummy.model;

public class RestAndBack {
	private Restaurant rest;
	private RestaurantBack back;

	public RestAndBack(Restaurant rest, RestaurantBack back) {
		this.rest = rest;
		this.back = back;
	}

	public Restaurant getRest() {
		return rest;
	}

	public void setRest(Restaurant rest) {
		this.rest = rest;
	}

	public RestaurantBack getBack() {
		return back;
	}

	public void setBack(RestaurantBack back) {
		this.back = back;
	}
}
