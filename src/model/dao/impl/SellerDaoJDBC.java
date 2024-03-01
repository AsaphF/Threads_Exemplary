package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	
	public SellerDaoJDBC (Connection conn) {
		this.conn = conn;
	};
	
	@Override
	public void insert(Seller seller) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, seller.getBirthDate());
			st.setDouble(4, seller.getBaseSalary());
			st.setObject(5, seller.getDepartment().getId());

			int rowsAffected = st.executeUpdate();
		    System.out.println("Number of rows affected: " + rowsAffected);
		    if (rowsAffected > 0) {
		    	ResultSet keyGenerated = st.getGeneratedKeys();
		    	while (keyGenerated.next()) {
					int id = keyGenerated.getInt(1);
					seller.setId(id);
				    System.out.println("id do Seller criado: " + id);
				}
		    }
			
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
	public void update(Seller seller) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ? ");

			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, seller.getBirthDate());
			st.setDouble(4, seller.getBaseSalary());
			st.setObject(5, seller.getDepartment().getId());
			st.setObject(6, seller.getId());


			int rowsAffected = st.executeUpdate();
		    System.out.println("Number of rows affected: " + rowsAffected);
		    if (rowsAffected > 0) {
		    	System.out.println("Seller atualizado com sucesso:");
		    }
			
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
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
	
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE id = ?");
			
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

	// Find Querys
	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
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
	public List<Seller> findByDepartment(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
	
		try {
			st = conn.prepareStatement(
				"SELECT seller.*, department.Name as DepName "
			  + "FROM seller INNER JOIN department "
			  + "ON seller.departmentId = department.Id "
			  + "WHERE department.id = ? "
			  + "ORDER BY Name"
				);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			List<Seller> listOfSellers = new ArrayList<>();
	        Department dep = null; 

	        while (rs.next()) {
	        	if (dep == null) {
					dep = instantiateDepartment(rs);
	        	}
				Seller seller = instantiateSeller(rs, dep);
				listOfSellers.add(seller);
			}
			
			return listOfSellers;

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
		Statement st = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department ON seller.departmentId = department.Id ORDER BY Name";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			List<Seller> listOfSellers = new ArrayList<>();
	        while (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);
				listOfSellers.add(seller);
			}
			
			return listOfSellers;

		} catch (Exception e) {
			// TODO: handle exception
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
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
