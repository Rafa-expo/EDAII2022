package org.eda2.practica01;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestNba {

	private static String rutaArchivo = System.getProperty("user.dir") + File.separator + "src" + File.separator + "org" 
										+ File.separator + "eda2" + File.separator + "practica01" + File.separator;
	

	//Comprueba la salida de los 10 mejores.
	@Test
	void test10MejoresDeNbaStats() {
		TopNba.loadFile(rutaArchivo + "NbaStats.csv");
		TopNba.top = 10;
		ArrayList<Player> array = TopNba.mejoresJug();
		assertEquals("[Wilt Chamberlain*: 1153 puntos, Kareem Abdul-Jabbar*: 1076 puntos, Michael Jordan*: 1075 puntos, George Gervin*: 1059 puntos, LeBron James: 1034 puntos, Karl Malone*: 1005 puntos, Karl-Anthony Towns: 965 puntos, Kevin Durant: 935 puntos, Oscar Robertson*: 925 puntos, Jerry West*: 854 puntos]", array.toString());
	}
	
	//Comprueba la salida de los 3 mejores.
	@Test
	void test3MejoresDeNbaStats() {
		TopNba.loadFile(rutaArchivo + "NbaStats.csv");
		TopNba.top = 3;
		ArrayList<Player> array = TopNba.mejoresJug();
		assertEquals("[Wilt Chamberlain*: 1153 puntos, Kareem Abdul-Jabbar*: 1076 puntos, Michael Jordan*: 1075 puntos]", array.toString());
		
	}
	
	
	//Comprueba que salte la excepcion al cargar un archivo sin datos (ArchivoVacio.csv)
	@Test
	void testVacio() {
		TopNba.loadFile(rutaArchivo + "ArchivoVacio.csv");
		TopNba.top = 10;
		try {
			ArrayList<Player> array = TopNba.mejoresJug();
			fail("No ha saltado ninguna excepcion");
		}catch (RuntimeException e) {
			assertEquals("No hay datos", e.getMessage());
		}
		
	}
	

}
