package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection coon;
	
	public SellerDaoJDBC (Connection coon) {
		this.coon = coon;
	};
	
	@Override
	public void insert(Seller eller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller eller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = coon.prepareStatement("DELETE FROM seller WHERE id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			int rowsAffected = st.executeUpdate(); // Execute the DELETE statement
		    System.out.println("Number of rows affected: " + rowsAffected);
		
		} catch (Exception e) {
			// TODO: handle exception
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = coon.prepareStatement(
				"SELECT seller.*, department.Name as DepName "
			  + "FROM seller INNER JOIN department "
			  + "ON seller.departmentId = department.Id "
			  + "WHERE seller.id = ?"
				);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			Department dep = instantiateDepartment(rs);

			Seller seller = instantiateSeller(rs, dep);
			
			return seller;

		} catch (Exception e) {
			// TODO: handle exception
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
		
	}
	@Override
	public void findByDepartment(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = coon.prepareStatement(
				"SELECT seller.*, department.Name as DepName "
			  + "FROM seller INNER JOIN department "
			  + "ON seller.departmentId = department.Id "
			  + "WHERE department.id = ? "
			  + "ORDER BY Name"
				);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
//			if (!rs.next()) {
//				return null;
//			}
			
			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}
			
//			Department dep = instantiateDepartment(rs);
//
//			Seller seller = instantiateSeller(rs, dep);
//			
//			return seller;

		} catch (Exception e) {
			// TODO: handle exception
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);
		return seller;
	}

}
