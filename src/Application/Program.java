package Application;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) throws ParseException {
		// Sellers Ops

//		SellerDao sd = DaoFactory.createSellerDao();
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        java.sql.Date sqlDate = new java.sql.Date(sdf.parse("29/04/1996").getTime());
//        // Criação manual (O correto é apresentar uma lista de departamentos e escolher de acordo.)
//        Department dep = new Department();
//        dep.setId(5);
//		// Insert
//		Seller seller = new Seller();
//		seller.setBaseSalary(500.00);
//		seller.setBirthDate(sqlDate);
//		seller.setName("Luke");
//		seller.setEmail("marksp@gmail.com");
//		seller.setDepartment(dep);
//		
//		sd.insert(seller);
//		Seller sellerToUpdate = sd.findById(1);
//		sellerToUpdate.setName("Bob Changed");
//		sd.update(sellerToUpdate);
//		System.out.println(sellerToUpdate.getName());
//		List<Seller> listByDep = sd.findByDepartment(1);
//		List<Seller> listAll = sd.findAll();
//
//		System.out.println(listByDep.size());
//		System.out.println(listByDep);
//		
//		System.out.println(lisstAll.size());
//		System.out.println(listAll);
		
		// Department Ops
		DepartmentDao dd = DaoFactory.createDepartmentDao();
		
		// Deletar departamento (É necessário verificar antes se esse departamento está em uso.)
//		dd.deleteById(6);
		
		// É sempre necessário informar o Id e os campos necessários para atualização
//		Department newDep = new Department(5, "RH");
//		dd.insert(newDep);
//		dd.update(newDep);
		List<Department> depsList = dd.findAll();
		System.out.println(depsList);
//		Department depSearch = dd.findById(7);
//		System.out.println(depSearch);
	}

}
