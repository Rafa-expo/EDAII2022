package org.eda2.practica02;

import java.util.List;

/**
 * Clase dedicada a obtener el coste total al ir de un punto a otro del grafo.
 *Esto lo hacemos recorriendo la lista resultado y sumando cada valor en una variable coste.
 *
 */
public class Costes {

	
private double coste = 0.0;
	

	
	public Costes (List<Arista> lista) {
		for (int i = 0; i < lista.size(); i++) {
			  coste = coste + lista.get(i).getPeso();
			}
	}

	public double getCoste() {
		return coste;
	}

	public void setCoste(double precioTotal) {
		this.coste = precioTotal;
	}
	
	public String toString() {
		return "\nCoste --> " + coste;
	}
}
