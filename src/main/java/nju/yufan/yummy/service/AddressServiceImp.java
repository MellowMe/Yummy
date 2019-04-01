package nju.yufan.yummy.service;

import nju.yufan.yummy.dao.AddressDao;
import nju.yufan.yummy.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImp implements AddressService {
	@Autowired
	private AddressDao addressDao;

	@Override
	public List<Address> getAddresses(int userid) {
		List<Address> list = addressDao.getAddresses(userid);
		list.sort((a1,a2)->-1*Boolean.compare(a1.isDefault(),a2.isDefault()));
		return list;
	}

	@Override
	public Address getDefault(int userid) {
		return addressDao.getDefault(userid);
	}

	@Override
	public int insert(Address address) {
		return addressDao.insert(address);
	}

	@Override
	public int deleteById(int id) {
		return addressDao.deleteById(id);
	}


	@Override
	public int setDefault(int id, int de ) {
		return addressDao.setDefault(id,de);
	}
}
