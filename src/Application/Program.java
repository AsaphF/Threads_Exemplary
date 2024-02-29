package Application;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		SellerDao sd = DaoFactory.createSellerDao();
//		Seller seller = sd.findByDepartment(1);
		sd.findByDepartment(1);
//		System.out.println(seller);
		
	}

}
// É necessário retornar uma lista com o nome de pessoas