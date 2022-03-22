package org.eda2.practica01;

import java.util.ArrayList;

/**
 * Con esta clase pretendemos hacer un análisis en tiempos de ejecución de nuestro algoritmo al intentar obtener el topN 
 * en casos donde contamos con un archivo de datos de tamaño variable.
 * 
 * Tiempos en nanosegundos.
 *
 */
public class Rendimiento {

	public static void main(String[] args) {
		long inicio;
		long fin;
		
		System.out.println("N\tTiempo");
		for(int i = 1; i<= 10000000; i*=2) {
			creaDatos(i, 50);
			inicio = System.nanoTime();
			TopNba.mejoresJug();
			fin = System.nanoTime();
			System.out.println(i + "\t" + (fin-inicio));
		
		}
	}

	/**
	 * Metodo que crea jugadores con puntuaciones aleatorias y los guarda en el arrayList estatico nba.
	 * @param N - numero total de jugadores.
	 * @param top - cantidad de mejores jugadores que queremos buscar (top 10, 50...)
	 */
	private static void creaDatos(int N, int top) {
		TopNba.nba = new ArrayList<Player>();
		for(int i = 0; i < N; i++) {
			Player jugador = new Player("Jugador"+i, "", "", (int) (Math.random()*N));
			TopNba.nba.add(jugador);
		}
		TopNba.top = top;

	}
	
}
