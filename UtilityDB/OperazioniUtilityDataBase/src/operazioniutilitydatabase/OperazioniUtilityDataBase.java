/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operazioniutilitydatabase;

import static OperazioniUtilityDataBase.Operazioni.insert;

/**
 *
 * @author riccardo.cozzi
 */
public class OperazioniUtilityDataBase {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String nomeTabella="Dati";
		String[]value=new String[2];
		value[0]="Mario";
		//value[1]="Gino";
		insert(nomeTabella,value);
	}
	
}
