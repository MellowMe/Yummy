package nju.yufan.yummy.model;

public class Address {
	private int id;
	private int userid;
	private String address;
	private boolean isDefault;

	public Address(int userid, String address, boolean isDefault) {
		this.userid = userid;
		this.address = address;
		this.isDefault = isDefault;
	}

	public Address() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean aDefault) {
		isDefault = aDefault;
	}
}
