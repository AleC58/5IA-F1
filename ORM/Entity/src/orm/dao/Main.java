package orm.dao;

import orm.entity.*;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		F1_DAO_Interface f1 = new F1_DAO_Implements();
		ArrayList<Integer> ris = f1.indiciTabella("Drivers");
		System.out.println(ris.toString());
	}
	
}
