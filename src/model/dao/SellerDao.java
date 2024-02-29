package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
	void insert(Seller eller);
	void update(Seller eller);
	void deleteById(Integer id);
	Seller findById(Integer id);
	void findByDepartment(Integer id);

	List<Seller> findAll();
}
