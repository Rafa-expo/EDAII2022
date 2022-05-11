package org.eda2.practica3;

import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.List;

/**
 * Clase se resuelven los distintos problemas planteados con el algoritmo de la mochila. 
 * Declaramos ArrayList que contiene los objetos y además una serie de atributos,
 * como la capacidad de la mochila, el peso y el valor final o el directorio donde están los archivos que vamos a cargar.
 * 
 * @author Rafael Expósito Mullor - Luis Díaz González
 *
 */

public class Mochila {

	private ArrayList<Objeto> objetos;
	private double pesoFinal;
	private double valorFinal;
	private int capacidad;

	private String inicioRuta = System.getProperty("user.dir") + File.separator
			+ "datasets" + File.separator;

	/**
	 * Constructor de mochila según los valores definimos en los archivos de datos.
	 * @param basefilename
	 */
	public Mochila(String basefilename) {
		try {
			cargarArchivo(basefilename);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Constructor de mochila con capacidad y número de objetos por parameto.
	 * @param capacidad - capacidad de la mochila.
	 * @param nTesoros - número de objetos.
	 */
	public Mochila(int capacidad, int numObjetos) {
		this.capacidad = capacidad;
		this.objetos = new ArrayList<Objeto>();
		for (int i = 1; i <= numObjetos; i++) {
			int peso = (int) (Math.random()*capacidad+1);
			int valor = (int) (Math.random()*(capacidad*2)+1);
			this.objetos.add(new Objeto("objeto"+i,peso , valor));
		}
	}

	/**
	 * Método de carga de archivos.
	 * @param basefilename - nombre por el que empiezan todos los archivos
	 * @throws FileNotFoundException
	 */
	private void cargarArchivo(String basefilename) throws FileNotFoundException {
		File f = new File(inicioRuta + basefilename + "_c.txt"); // formato del archivo -> nombreBase + _c.txt, _w.txt o _p.txt
		this.objetos = new ArrayList<Objeto>();
		Scanner sc = new Scanner(f);
		this.capacidad = Integer.parseInt(sc.nextLine().trim());
		sc.close();

		f = new File(inicioRuta + basefilename + "_w.txt");
		sc = new Scanner(f);

		int i = 1;
		while (sc.hasNext()) { //inicializar nombre y peso

			this.objetos.add(new Objeto("Objeto" + i, Double.parseDouble(sc.nextLine()), 1.00));
			i++;
		}

		sc.close();
		f = new File(inicioRuta + basefilename + "_p.txt");
		sc = new Scanner(f);

		i = 0;
		while (sc.hasNext()) {
			this.objetos.get(i).setValor(Double.parseDouble(sc.nextLine()));
			i++;

		}

		sc.close();
	}

	/**
	 * Algoritmo clásico de la mochila.
	 * Los objetos se ordenan de forma ascendente según el peso, de tal forma que se
	 * valoren primero los objetos con menor peso y en último lugar los que mayor
	 * peso tengan. 
	 * Se trabaja con una matriz de tamaño (N+1) x (C+1), donde N representa el número de objetos y C la capacidad o peso de la mochila.
	 * Se ha aplicado el cálculo del mcd para recudir el tamaño de la matriz resultante.
	 * 
	 * @return - Llamada al método RecuperarObjetos para saber los objetos que formarán parte de la mochila.
	 */
	public ArrayList<Objeto> mochila() {
		this.objetos.sort(null); 
		int n = this.objetos.size();
		int dec = numeroMaximoDecimales();
		int mcd = mcd(dec);
		double[][] B = new double[n + 1][(capacidad * dec)/mcd + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= (capacidad * dec)/mcd; j++) {
				if ((this.objetos.get(i - 1).getPeso() * dec)/mcd <= j) {
					B[i][j] = Math.max(B[i - 1][j], this.objetos.get(i - 1).getValor()
							+ B[i - 1][j - (int) (this.objetos.get(i - 1).getPeso() * dec)/mcd]);
				} else {
					B[i][j] = B[i - 1][j];
				}
			}
		}
		return RecuperarObjetos(B, mcd, dec); 
	}

	/**
	 * Método para recuperar los objetos.
	 * 
	 * Desde última celda, verificaremos si la situada justo encima tiene un valor distinto. 
	 * Si el valor es igual, significará que el objeto de la fila actual no pertenece al conjunto resultado, 
	 * por lo que nos desplazaremos a la fila superior. 
	 * Si el valor es distinto, significará que el objeto de la fila si debe pertenecer al
	 * resultado, por lo que desplazaremos a la fila superior y nos desplazaremos el peso del objeto escogido a la izquierda.
	 * 
	 * @param B - matriz de objetos
	 * @return - resultados: objetos que van a ser introducidos en la mochila
	 */
	public ArrayList<Objeto> RecuperarObjetos(double[][] B, int mcd, int dec) {
		ArrayList<Objeto> resultados = new ArrayList();
		valorFinal = B[B.length - 1][B[0].length - 1];// el valor que tendra la mochila al final
		pesoFinal = 0;
		int j = B[0].length - 1; //Empezamos por aquí
		for (int i = B.length - 1; i > 0; i--) { // subir de fila
			if (B[i][j] != B[i - 1][j]) {// celda superior distinta?
				resultados.add(this.objetos.get(i - 1));
				pesoFinal += this.objetos.get(i - 1).getPeso();
				j -= (this.objetos.get(i - 1).getPeso()*dec)/mcd;// Se desplaza la columna a la izquierda el peso del objeto
			}
		}
		return resultados;
	}

	/**
	 * Algoritmo de la mochila para objetos ilimitados.
	 * 
	 * Contamos con un array de tamaño P+1, de forma que array[ i ] almacene el valor máximo que se
	 * puede lograr usando todos los elementos bajo una capacidad de mochila i. 
	 * El primer paso es ordenar los elementos de forma ascendente por su peso, después se valora qué es mejor, 
	 * si el valor que habia en el array en la posición i (valor antiguo) o el valor de la celda desplazada el
	 * peso de elemento a la izquierda más su propio valor.
	 * 
	 * @return - Objetos que que se pueden introduir finalmente dentro de la mochila.
	 *         
	 */
	public ArrayList<Objeto> mochilaIlimitada() {
		this.objetos.sort(null); 
		int n = this.objetos.size();
		double[] array = new double[capacidad + 1]; 
		for (int i = 0; i <= capacidad; i++) {
			for (int j = 0; j < n; j++) {
				if (this.objetos.get(j).getPeso() <= i) {
					array[i] = Math.max(array[i],
							array[i - (int) this.objetos.get(j).getPeso()] + this.objetos.get(j).getValor());
				}
			}
		}
		return recuperarIlimitados(array);
	}

	/**
	 * Método para seleccionar los objetos que se introducirán finalmente en la mochila (versión ilimitada).
	 * 
	 * El algoritmo permanecerá en bucle mientras la capacidad restante sea superior al menor de los pesos de los objetos. 
	 * Lo que haremos será buscar aquel objeto que, en el espacio que queda de la mochila, más valor nos proporcione. 
	 * Una vez se encuentre el mejor candidato, se agrega a la solución y se decrementa el espacio de la mochila.
	 * 
	 * @param array
	 * @return resultados
	 */
	private ArrayList<Objeto> recuperarIlimitados(double[] array) {
		valorFinal = 0;
		pesoFinal = 0;
		ArrayList<Objeto> resultados = new ArrayList();
		int c = this.capacidad; 
		double pesoMinimo = this.objetos.get(0).getPeso(); 
		int n = this.objetos.size();
		while (c >= pesoMinimo) { // Puede quedar espacio sin ocupar
			double maxValue = 0;
			int item = -1; // Posición del objeto ganador
			for (int i = n - 1; i >= 0; i--) { // Para cada objeto...
				if (c - this.objetos.get(i).getPeso() >= 0) { // Valoraremos solo los objetos que quepan en el espacio
																// restante
					double newValue = array[c - (int) this.objetos.get(i).getPeso()] + this.objetos.get(i).getValor();
					if (newValue > maxValue) {
						maxValue = newValue;
						item = i;
					}
				}
			}
			if (item == -1)
				break; // Si no se encuentra un candidato factible, se finaliza la búsqueda
			valorFinal += this.objetos.get(item).getValor();
			pesoFinal += this.objetos.get(item).getPeso();
			resultados.add(this.objetos.get(item));
			c -= this.objetos.get(item).getPeso(); // Decrementamos el espacio restante de la mochila
		}
		return resultados;
	}

	/**
	 * Calcula el máximo común divisor de dos números.
	 * @param a
	 * @param b
	 * @return a
	 */
	private int mcd(int a, int b) {
		while (b > 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}

	/**
	 * Aplica el uso del mcd a los objetos y la capacidad.
	 * @return result
	 */
	public int mcd(int dec) {
		int result = capacidad*dec;
		for (int i = 0; i < objetos.size(); i++) {
			result = mcd(result, (int) objetos.get(i).getPeso());
			if (result == 1)
				return 1;
		}
		return result;
	}

	/**
	 * Método que devuelve el número de decimales de un valor.
	 * 
	 * @param num - número 
	 * @return parteDecimal
	 */
	private int numerosDecimales(double num) {
		if (num == (int) num) return 0; //Comtempla el caso de que se pase un entero. P.e: 7.0 => 0 decimales
		String texto = Double.toString(Math.abs(num));
		int parteEntera = texto.indexOf('.');
		if (parteEntera == -1)
			return 0;
		int parteDecimal = texto.length() - parteEntera - 1;
		return parteDecimal;
	}

	/**
	 * Método para saber el máximo de número decimales de un valor. 
	 * Concretamente de la capacidad de la mochila y pesos de los objetos, para así resolver el problema
	 * de utilizar pesos decimales en el algoritmo de la mochila.
	 * 
	 * @return factor por el que multiplicar los pesos decimales.
	 */
	private int numeroMaximoDecimales() {
		int maxDec = numerosDecimales(capacidad);
		for (Objeto ob : objetos) {
			int n = numerosDecimales(ob.getPeso());
			maxDec = n > maxDec ? n : maxDec;
		}
		return (int) Math.pow(10, maxDec);
	}

	/**
	 * Algoritmo Greedy para resolver el problema de la mochila fraccionaria.
	 * 
	 * La idea básica es que si escogemos un objeto por completo estableceremos el atributo cantidad a 1 
	 *(representa el 100% del peso) y si lo escogemos parcialmente le establecemos un valor entre 0 y 1 
	 *(representa un valor entre 0% y 100%). En el caso de introducir un elemento parcialmente, 
	 *el algoritmo finalizará puesto que ese es el último elemento que podrá introducirse.
	 * 
	 * @return resultados
	 */
	public ArrayList<Objeto> mochilaGreedy() {
		this.objetos.sort(new ComparadorGreedy()); // De mayor a menor -> relacion valor(peso
		valorFinal = 0;
		pesoFinal = 0;
		ArrayList<Objeto> resultados = new ArrayList();
		for (Objeto obj : objetos) {
			if (pesoFinal + obj.getPeso() <= capacidad) { // Si el objeto entra entero...
				obj.setCantidad(1);
				resultados.add(obj);
				pesoFinal += obj.getPeso();
				valorFinal += obj.getValor();
				if (pesoFinal == capacidad)
					break; // Si no entra nada más se para.
			} else { //Si el obj no entra entero...
				obj.setCantidad((capacidad - pesoFinal) / obj.getPeso());
				resultados.add(obj);
				pesoFinal += obj.getPeso() * obj.getCantidad();
				valorFinal += obj.getValor() * obj.getCantidad();
				break; //Finalizamos porque ya no entra nada más.
			}
		}
		return resultados;
	}
}
