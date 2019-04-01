package nju.yufan.yummy.service;

import nju.yufan.yummy.model.Address;

import java.util.List;

public interface AddressService {
	List<Address> getAddresses(int userid);

	Address getDefault(int userid);

	int insert(Address address);

	int deleteById(int id);

	int setDefault(int id,int de);
}
