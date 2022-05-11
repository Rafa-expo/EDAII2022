package org.eda2.practica3;

/**
 * Clase para la medición de tiempos de ejecución de los distintos algoritmos.
 * @author Rafael Expósito Mullor - Luis Díaz González
 *
 */
public class MainTiempos {

	public static void main(String[] args) {

		long empieza, acaba, tMochilaC, tMochilaI, tMochilaG;

		System.out.println("NumObj\tCap\t\tMclasica\tMinfinita\tMGreedy");
		for (int i = 1; i <= 8192; i = i * 2) {

			Mochila m = new Mochila(i, 80);

			empieza = System.nanoTime();
			m.mochila();
			acaba = System.nanoTime();
			tMochilaC = acaba - empieza;
			empieza = System.nanoTime();
			m.mochilaIlimitada();
			acaba = System.nanoTime();
			tMochilaI = acaba - empieza;
			empieza = System.nanoTime();
			m.mochilaGreedy();
			acaba = System.nanoTime();
			tMochilaG = acaba - empieza;

			System.out.println("20\t " + i + "\t\t" + tMochilaC + "\t\t" + tMochilaI + "\t\t" + tMochilaG);

		}

//		System.out.println("NumObj\tCap\tMClasica\tMInfinita\tMGreedy");
//		for (int i = 1; i <= 8192; i = i * 2) {
//
//			Mochila m = new Mochila(80, i);
//
//			empieza = System.nanoTime();
//			m.mochila();
//			acaba = System.nanoTime();
//			tMochilaC = acaba - empieza;
//			empieza = System.nanoTime();
//			m.mochilaIlimitada();
//			acaba = System.nanoTime();
//			tMochilaI = acaba - empieza;
//			empieza = System.nanoTime();
//			m.mochilaGreedy();
//			acaba = System.nanoTime();
//			tMochilaG = acaba - empieza;
//
//			System.out.println(i + "\t" + "20\t " + tMochilaC + "\t" + tMochilaI + "\t\t" + tMochilaG);
//		}
				
		
	}

}
