package nju.yufan.yummy.model;

import java.sql.Timestamp;

public class Token {
	private String token;
	private Timestamp expireTime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Timestamp expireTime) {
		this.expireTime = expireTime;
	}
}
