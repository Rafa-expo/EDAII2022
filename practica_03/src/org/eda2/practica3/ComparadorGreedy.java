package org.eda2.practica3;

import java.util.Comparator;
/**
 * Clase utilizada por el algoritmo greedy. Creada para ordenar descendentemente los objetos por su relación valor/peso.
 * @author Rafael Expósito Mullor - Luis Díaz González
 */
public class ComparadorGreedy implements Comparator<Objeto>{

		/**
		 * Método que compara por la relacion peso/valor.
		 */
		@Override
		public int compare(Objeto o1, Objeto o2) {
			int comp = -Double.compare(o1.getValor()/o1.getPeso(), o2.getValor()/o2.getPeso());
			return comp == 0 ? o1.getNombre().compareTo(o2.getNombre()) : comp;
		}

}
