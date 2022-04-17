package org.eda2.practica02;

public class Arista {

	private String origen;
	private String destino;
	private double peso;
	
	/**
	 * Constructor de arista.
	 * @param origen
	 * @param destino
	 * @param peso
	 */
	public Arista(String origen, String destino, double peso) {
		this.origen = origen;
		this.destino = destino;
		this.peso = peso;
	}

	/**
	 * Obtiene el origen.
	 * @return origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * Establece un origen
	 * @param origen
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * Obtiene el destino.
	 * @return destino
	 */
	public String getDestino() {
		return destino;
	}
 
	/**
	 * Establece un destino.
	 * @param destino
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}

	/**
	 * Obtiene el peso.
	 * @return
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * Establece un peso.
	 * @param peso
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "[" + origen + "->" + "[" + destino  +  peso +  "]]";
	}
	
	
	
	
}
