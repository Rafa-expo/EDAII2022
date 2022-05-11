package org.eda2.practica3;

/**
 * Clase para representar los objetos. Tendremos los atributos nombre, peso,
 * valor y cantidad del objeto
 * 
 * @author Rafael Expósito Mullor - Luis Díaz González
 * 
 *
 */
public class Objeto implements Comparable<Objeto>{

	/**
	 * Declaramos los atributos.
	 */
	private String nombre;
	private double peso;
	private double valor;
	private double cantidad;

	/**
	 * Contructor de objetos por defecto.
	 * 
	 * @param nombre
	 * @param peso
	 * @param valor
	 */
	public Objeto(String nombre, double peso, double valor) {
		this.nombre = nombre;
		this.peso = peso;
		this.valor = valor;
		this.cantidad = 1;
	}

	/**
	 * Obtiene el nombre.
	 * 
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene el peso del objeto.
	 * @return peso
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * Obtiene el valor de objeto.
	 * @return valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * Establece el valor del objeto.
	 * @param valor
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * Obtiene la cantidad del objeto.
	 * @return
	 */
	public double getCantidad() {
		return cantidad;
	}

	/**
	 * Establece la cantidad del objeto.
	 * @param cantidad
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return nombre + ", peso=" + (peso * cantidad) + ", valor=" + (valor * cantidad);
	}
	
	/**
	 * compareTo
	 * compara por peso y valor
	 */
	@Override
	public int compareTo(Objeto o) {
		int comp =  Double.compare(this.peso, o.peso);
		return comp == 0 ?  Double.compare(this.valor, this.valor) : comp;
	}

}
