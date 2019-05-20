package Utilities;

import DB.OperazioniDB;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;

public class Utility {

	public static final String URL = "http://ergast.com/mrd/db/";
	public static final String PATH_DIR_CSV = "resources\\csvFiles";
	public static final String BASE = "<meta property=\"article:modified_time\"";
	public static final String URL_FOR_DOWNLOAD = "http://ergast.com/downloads/f1db_csv.zip";
	public static final String SAVE_PATH = "C:\\Users\\raoul.giurin\\Desktop\\PerNonSaperNeLeggereNeScrivere";
	public static String Date = "<meta property=\"article:modified_time\" content=\"2019-05-12T21:32:11+00:00\" />";

	public static void fromCSVToTable(String[] csv, String tableName) throws ClassNotFoundException, SQLException { //

		OperazioniDB.insRec(tableName, csv);

	}

	public static String[] readLineFileCSV(String fileCSV, int number) {
		String csvFile = PATH_DIR_CSV + "\\" + fileCSV;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] values = null;

		try {

			br = new BufferedReader(new FileReader(csvFile));

			// use comma as separator
			line = br.readLine();

			for (int i = 0; i < number && line != null; i++) {
				values = line.split(cvsSplitBy);
				line = br.readLine();
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
		return values;
	}

	public static int contaRighe(String fileCSV) {
		String csvFile = PATH_DIR_CSV + "\\" + fileCSV;
		BufferedReader br = null;
		int ris = 0;

		try {

			br = new BufferedReader(new FileReader(csvFile));

			while (br.readLine() != null) {
				ris++;
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

		return ris;
	}

	public static void downloadZIP(int readTimeoutInSeconds) throws MalformedURLException, IOException {

		//create connection, setting a timeout
		URLConnection urlConn = new URL(URL).openConnection();
		if (readTimeoutInSeconds > 0) {
			urlConn.setReadTimeout(readTimeoutInSeconds * 1000);
		}

		//create the stream
		BufferedInputStream in = new BufferedInputStream(urlConn.getInputStream());
		FileOutputStream fos = new FileOutputStream(SAVE_PATH + "f1db_csv.zip");

		//read the file
		BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
		byte[] data = new byte[1024];
		int x = 0;
		while ((x = in.read(data, 0, 1024)) >= 0) {
			fos.write(data, 0, x);
		}
		bout.close();
		in.close();
	}

	public static boolean getChangesDate() throws Exception {
		boolean ris = false;
		URL cg = new URL(URL);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(cg.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains(BASE)) {
				if (!inputLine.equals(Date)) {
					Date = inputLine;
					ris = true;
				}
			}
		}

		in.close();
		return ris;
	}
}
