package org.eda2.practica04;

import java.io.File;
import java.util.ArrayList;

public class Main {
	
	private static String path = System.getProperty("user.dir") + File.separator + "datasets" + File.separator; 

	public static void main(String[] args) {
		String filename = "si.txt";
		File f = new File(path + filename);
		TSP b = new TSP(f, "Almeria");
		ArrayList<ArrayList<String>> results = b.BackTracking();
	}

}
