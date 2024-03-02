package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class DepartmentDaoJDBC implements DepartmentDao{

	private Connection conn;
	
	public DepartmentDaoJDBC (Connection conn) {
		this.conn = conn;
	};
	
	@Override
	public void insert(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO department (Id, Name) VALUES (?,?)");
			st.setInt(1, department.getId());
			st.setString(2, department.getName());

			
			int rowsAffected = st.executeUpdate(); // Execute the DELETE statement
		    System.out.println("Number of rows affected: " + rowsAffected);
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
	}
	
	@Override
	public void update(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
			st.setString(1, department.getName());
			st.setInt(2, department.getId());


			
			int rowsAffected = st.executeUpdate(); // Execute the DELETE statement
		    System.out.println("Number of rows affected: " + rowsAffected);
			
		} catch (Exception e) {
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
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			st.setInt(1, id);
						
			int rowsAffected = st.executeUpdate(); // Execute the DELETE statement
		    System.out.println("Number of rows affected: " + rowsAffected);
			
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (!rs.next())  {
				return null;
			}

			Department depSearched = instantiateDepartment(rs);
			
			return depSearched;
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM department");
			
			List<Department> depsList = new ArrayList<>();
			
			if (!rs.next())  {
				System.out.println("Nenhum departamento criado até o momento.");
				return null;
			}
			
			while (rs.next()) {
				Department depSearched = instantiateDepartment(rs);
				depsList.add(depSearched);
			}
			
			return depsList;
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

}
