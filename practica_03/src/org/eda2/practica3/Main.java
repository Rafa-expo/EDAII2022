package org.eda2.practica3;

import java.util.ArrayList;

/**
 * Clase para comprobar el correcto funcionamiento del algoritmo de la mochila clásica 
 * para los archivos de datos p01 proporcionados para esta práctica, los cuales indican
 * la capacidad de la mochila, el valor y el peso de los objetos.
 * Se muestran los objetos seleccionados.
 * 
 * @author Rafael Expósito Mullor - Luis Díaz González
 *
 */
public class Main {

	public static void main(String[] args) {
		
		Mochila mochila = new Mochila("p01");

		ArrayList<Objeto> sol = mochila.mochila();

		for (Objeto tesoro : sol) {
			System.out.println(tesoro.toString());
		}

	}
	

}
