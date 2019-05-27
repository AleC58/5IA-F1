package Utilities;

import DB.OperazioniDB;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Utility {
	
	public static final String URL = "http://ergast.com/mrd/db/#csv";
	public static final String PATH_DIR_CSV = "resources\\csvFiles";
	public static final String BASE = "<meta property=\"article:modified_time\"";
	public static final String URL_FOR_DOWNLOAD = "http://ergast.com/downloads/f1db_csv.zip";
	public static final String SAVE_PATH = "E:\\Giurin\\TRJ\\LavoroF1\\DbCsv";
	
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
			line = correggi(line);
			
			for (int i = 0; i < number && line != null; i++) {
				values = line.split(cvsSplitBy);
				line = br.readLine();
				if (line != null) {
					line = correggi(line);
				}
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
	
	private static String correggi(String line) {
		
		int index = 0;
		
		String risultato = line;
		
		while (index < line.length() && line.charAt(index) != '/') {
			index++;
		}
		
		for (int i = index; i < line.length(); i++) {
			if (line.charAt(i) == ',') {
				risultato = line.substring(0, i) + line.substring(i + 1);
			}
		}
		return risultato;
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
	
	public static boolean getChangesDate() throws Exception {
		boolean ris = false;
		URL cg = new URL(URL);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(cg.openStream()));
		Scanner inputXML = new Scanner(new File("properties.xml"));
		PrintWriter fileOut;
		String inputLine;
		String inputXMLLine=inputXML.nextLine();
		boolean flag = true;
		while (inputXML.hasNext() && flag) {
			if (inputXMLLine.contains(BASE)) {
				flag = false;
			}else{
				inputXMLLine=inputXML.nextLine();
			}
		}
		
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.contains(BASE)) {
				if (!inputLine.equals(inputXMLLine)) {
					fileOut = new PrintWriter(new FileWriter("properties.xml"));
					fileOut.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n<Data> \r\n");
					fileOut.print(""+inputLine);
					fileOut.print("\r\n");
					fileOut.print("</Data>");
					fileOut.close();
					ris = true;
				}
			}
		}
		inputXML.close();
		
		in.close();
		return ris;
	}
	
	public static void downloadZIP() {
		try {
			URL website = new URL(URL_FOR_DOWNLOAD);
			try (
					ReadableByteChannel rbc = Channels.newChannel(website.openStream());
					FileOutputStream fos = new FileOutputStream(SAVE_PATH + "\\f1db_csv.zip")) {
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public static void unzip() throws IOException {
		String zipFilePath = "f1db_csv.zip";
		String destDirectory = "resources\\csvFiles";
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				extractFile(zipIn, filePath);
			} else {
				// if the entry is a directory, make the directory
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}

	/**
	 * Extracts a zip entry (file entry)
	 *
	 * @param zipIn
	 * @param filePath
	 * @throws IOException
	 */
	private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[4096];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
}
