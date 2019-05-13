package provacsv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author raoul.giurin
 */
public class ProvaCsv {
	public static void main(String[] args) {

		String csvFile = "C:\\Users\\raoul.giurin\\Desktop\\LavoroF1\\PopulationDb\\Dati\\circuits.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] circuito = line.split(cvsSplitBy);
				
				System.out.println("Circuito [Code= " + circuito[0] + ", Ref= " + circuito[1] + 
						", Name= " + circuito[2] + ", Location= " + circuito[3] + ", Country= " + circuito[4] + 
						", Lat= " + circuito[5] + ", Lng= " + circuito[6] + ", Alt= " + circuito[7] + 
						", Url= " + circuito[8] + "]");
			}
            
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
