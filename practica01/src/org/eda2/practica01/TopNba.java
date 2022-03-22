package org.eda2.practica01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TopNba {

	private static String rutaArchivo = System.getProperty("user.dir") + File.separator + "src" + File.separator + "org" 
			+ File.separator + "eda2" + File.separator + "practica01" + File.separator + "NbaStats.csv";
	
	public static ArrayList<Player> nba;
	public static int top = 10;
	
	public static void main(String[] args) {
		loadFile(rutaArchivo);
		
		long start = System.currentTimeMillis();
		mejoresJug();
		long end = System.currentTimeMillis();
		System.out.println("Tiempo ejecucion: " + (end-start) + " ms");

	}
	
	/**
	 * Metodo publico que devuelve los mejores jugadores. 
	 * Se encarga de llamar al metodo privado en su ejecucion 
	 * ademas de controlar si no hay datos almacenados. 
	 * 
	 * @return ganadores
	 */
	public static ArrayList<Player> mejoresJug() {
		if(nba.size() == 0) {
			System.out.println("No hay jugadores/datos");
			throw new RuntimeException("No hay jugadores/datos");
		} else {
			ArrayList<Player> ganadores = mejoresJug(0, nba.size()-1);
			System.out.println("MEJORES " + top + " JUGADORES: ");
			for (Player p : ganadores) {
				System.out.println(p);
			}
			return ganadores;
		}
	}
	
	/**
	 * Metodo publico que obtiene el top de jugadores especificado. 
	 * Se encarga de dividir en subarrays, ordenarlos recursivamente y combinarlos.
	 * @param inicio - indice inicio para los subarrays.
	 * @param fin - indice fin para los subarrays.
	 * @return resultado
	 */
	private static ArrayList<Player> mejoresJug(int inicio, int fin) {
		ArrayList<Player> resultado = new ArrayList<Player>(top); //
		if(inicio == fin) {
			resultado.add(nba.get(inicio));
		}else{
			int mitad = (inicio + fin)/2;
			ArrayList<Player> parte1 = mejoresJug(inicio, mitad);
			ArrayList<Player> parte2 = mejoresJug(mitad+1, fin);
			
			int i = 0;
			int j = 0;
			//Mientras haya elementos en los dos lados, derecho e izquierdo
			while(resultado.size() < top && i <= parte1.size()-1 && j<= parte2.size()-1) {
				if(parte1.get(i).getScore() > parte2.get(j).getScore()) {
					resultado.add(parte1.get(i));
					i++;
				}else {
					resultado.add(parte2.get(j));
					j++;
				}
			}
			//Si hay elementos en un lado y en otro no.
			while(resultado.size() < top && i<= parte1.size()-1) {
				resultado.add(parte1.get(i));
				i++;
			}
			while(resultado.size() < top && j <= parte2.size()-1) {
				resultado.add(parte2.get(j));
				j++;
			}
		}
		return resultado;
	}

	
	/**
	 * Metodo de carga para el archivo de datos que contiene los jugadores.
	 * @param rutaArchivo
	 */
	public static void loadFile(String rutaArchivo) {
		nba = new ArrayList<Player>();
		try {
			Scanner sc = new Scanner(new File(rutaArchivo));
			String linea = "";
			String[] tokens;
			Player ultimoJugadorCargado = null;
			String ultimoNombreCargado = "";
			
			while(sc.hasNextLine()) {
				linea = sc.nextLine().trim();
				if(linea.isEmpty() || linea.startsWith("#")) continue;
				tokens = linea.split(";");
				//Si faltan campos se descarta
				if(tokens.length != 9) continue;
				double fg = comprobarElemento(tokens[7]);
				double puntos = comprobarElemento(tokens[8]);
				if(!tokens[2].equals(ultimoNombreCargado)) {
					ultimoJugadorCargado = new Player(tokens[2], tokens[6], tokens[4], (int) (fg*puntos/100));
					nba.add(ultimoJugadorCargado);
					ultimoNombreCargado = tokens[2];
				} else {
					ultimoJugadorCargado.actualizar(tokens[6], tokens[4],  (int) (fg*puntos/100)); //equipo, posicion y puntuacion.
				}
			}
			sc.close();
		}catch(FileNotFoundException e) {
			System.out.println("Error al cargar el archivo");
		}	
	}

	/**
	 * Metodo que transforma de String a Double un elemento para poder trabajar con el.
	 * Ademas comprueba que no sea un elemento vacio.
	 * @param value - parametro a comprobar.
	 * @return elementoNuevo
	 */
	private static double comprobarElemento(String value) {
		if(value.isEmpty()) return 0;
		
		try {
			double elementoNuevo = Double.parseDouble(value.replace(",", "."));
			return elementoNuevo;
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
