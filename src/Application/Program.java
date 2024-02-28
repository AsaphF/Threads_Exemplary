package Application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mode.entities.Department;
import mode.entities.Seller;

public class Program {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "29/04/1997";
        Date date = null;
        try {
            date = sdf.parse(dateInString);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		Department obj = new Department (1, "books");
		Seller sellerObj = new Seller (1, "Asaph", "asaphrr@gmail.com", date, 4500.0);

		System.out.println(obj);
		System.out.println(sellerObj);

	}

}
