package org.eda2.practica02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class EDAlandTest {
	
	private static String ruta = System.getProperty("user.dir") + File.separator + "src" + File.separator + "org" + File.separator +
			"eda2" + File.separator + "practica02" + File.separator;

	
	@Test
	public void ComprobarSalidaKruskal() {
		
		EDAland proceso = new EDAland (ruta + "graphPrimKruskal.txt", "A");
		
		List<Arista> arrayKruskal = proceso.kruskal();
		
		ArrayList<Arista> arrayEsperado = new ArrayList<Arista>();
		arrayEsperado.add(new Arista("B", "E", 13));
		arrayEsperado.add(new Arista("C", "A", 5));
		arrayEsperado.add(new Arista("D", "C", 21));
		arrayEsperado.add(new Arista("E", "C", 17));
		arrayEsperado.add(new Arista("F", "D", 9));

		assertEquals(arrayEsperado.toString(), arrayKruskal.toString());
	}
	
	
	@Test
	public void PrimConOrigenEquivocado(){
		EDAland arbol = new EDAland (ruta + "graphPrimKruskal.txt", "1");
		try {
			arbol.prim();
			fail("Tendría que haber saltado una excepción. Inténtalo de nuevo.");
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "No puede ser nulo.");
		}
	}
	
	@Test
	public void PrimPQConOrigenEquivocado(){
		EDAland proceso = new EDAland (ruta + "graphPrimKruskal.txt", "1");
		try {
			proceso.primPQ();
			fail("Tendría que haber saltado una excepción. Inténtalo de nuevo.");
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "No puede ser nulo.");
		}
	}
	
	@Test
	public void KruskalConOrigenEquivocado(){
		EDAland arbol = new EDAland (ruta + "graphPrimKruskal.txt", "1");
		try {
			arbol.kruskal();
			fail("Tendría que haber saltado una excepción. Inténtalo de nuevo.");
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "No puede ser nulo.");
		}
	}
}
