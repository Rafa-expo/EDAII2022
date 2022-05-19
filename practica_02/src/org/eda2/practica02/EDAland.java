package org.eda2.practica02;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Esta clase contiene los respectivos algortimos de Prim y Kruskal.
 * Para la creación del grafo se ha utilizado un HashMap que tendra como clave el vertice inicial y como valor otro HashMap 
 * con el vertice final y el peso entre ambos.
 *
 */
public class EDAland {

	private final double INFINITO = Double.MAX_VALUE;
	private boolean esDirigido;
	private HashMap<String, HashMap<String, Double>> grafo;// Key=Vertice, Key2=, Vertice, Value=Peso
	private String origen;

	private static String rutaArchivo = System.getProperty("user.dir") + File.separator + "src" + File.separator + "org"
			+ File.separator + "eda2" + File.separator + "practica02" + File.separator + "graphEDAland.txt";



	public static void main(String[] args) {
		EDAland tree = new EDAland(rutaArchivo, "0");

		List<Arista> resultado = tree.prim();
		System.out.println(resultado);
		Costes coste = new Costes(resultado);
		System.out.println(coste);

		List<Arista> resultado1 = tree.primPQ();
		System.out.println(resultado1);
		Costes coste1 = new Costes(resultado1);
		System.out.println(coste1);

		List<Arista> resultado2 = tree.kruskal();
		System.out.println(resultado2);
		Costes coste2 = new Costes(resultado2);
		System.out.println(coste2);
	}

	
	/**
	 * Constructor por defecto. Crea un grafo vacio.
	 */
	public EDAland() {
		this.esDirigido = true;
		this.grafo = new HashMap<String, HashMap<String, Double>>();
	}


	/**
	 * Segundo metodo constructor que utilizaremos para cargar el archivo.
	 * @param archivo
	 * @param origen
	 */
	public EDAland(String archivo, String origen) {
		this();
		this.origen = origen;
		Scanner scan = null;
		String linea = "";
		String[] aux = null;

		try {
			scan = new Scanner(new File(archivo));
		} catch (Exception ex) {
			System.out.println("Error al cargar el archivo.");
			System.exit(-1);
		}

		linea = scan.nextLine();
		setEsDirigido(linea.equals("0") ? false : true);

		linea = scan.nextLine();
		for (int i = 0; i < Integer.valueOf(linea); i++) {
			agregarVertice(scan.nextLine());
		}

		linea = scan.nextLine();
		for (int i = 0; i < Integer.valueOf(linea); i++) {
			aux = scan.nextLine().split(" ");
			agregarArista(aux[0], aux[1], Double.parseDouble(aux[2]));
		}
	}

	/**
	 * Devuelve si el grafo es o no dirigido.
	 * 
	 */
	public boolean getEsDirigido() {
		return this.esDirigido;
	}

	/**
	 * Establece si el grafo es o no dirigido.
	 * @param esDirigido
	 */
	public void setEsDirigido(boolean esDirigido) {
		this.esDirigido = esDirigido;
	}


	/**
	 * Agrega un vertice al grafo.
	 * @param vertice
	 * @return true si todo correcto.
	 */
	public boolean agregarVertice(String vertice) {
		if (this.grafo.containsKey(vertice))
			return false;
		this.grafo.put(vertice, new HashMap<String, Double>());
		return true;
	}

	/**
	 * Agrega aristas al grafo. si el grafo no es dirigido se añade una arista en sentido contrario.
	 * @param v1 - vertice 1
	 * @param v2 - vertice 2
	 * @param peso
	 * @return true si todo correcto.
	 */
	public boolean agregarArista(String v1, String v2, double peso) {
		agregarVertice(v1);
		agregarVertice(v2);
		this.grafo.get(v1).put(v2, peso);
		if (!this.esDirigido) { // Si no es dirigido, es necesario añadir una arista en sentido contrario (A-B, B-A)
			this.grafo.get(v2).put(v1, peso);
		}
		return true;
	}

	/**
	 * Metodo que comprueba si un grafo contiene una arista.
	 * @param v1 - vertice1
	 * @param v2 - vertice2
	 */
	private boolean contieneArista(String v1, String v2) {
		if (this.grafo.get(v1) != null && this.grafo.get(v1).get(v2) != null)
			return true;
		return false;
	}

	/**
	 * Obtiene el peso.
	 * @param v1 - vertice1
	 * @param v2 - vertice2
	 */
	public Double getPeso(String v1, String v2) {
		return contieneArista(v1, v2) ? this.grafo.get(v1).get(v2) : null;
	}


	/**
	 * Algoritmo de Prim. Dado un grafo conexo, no dirigido y ponderado, encuentra un arbol de recubrimiento mínimo.
	 * @return resultado
	 */
	public List<Arista> prim() {
		long startNano = System.nanoTime();
		long startMili = System.currentTimeMillis();

		if (origen == null || !this.grafo.containsKey(origen))
			throw new RuntimeException("No puede ser nulo.");

		HashMap<String, Double> pesos = new HashMap<String, Double>(); // key = vertice, Value = peso
		HashMap<String, String> aristas = new HashMap<String, String>(); // Key = vertice inicial, Value = vertice final
		HashSet<String> restantes = new HashSet<String>();
		List<Arista> resultado = new ArrayList<Arista>();
		String desde = null;

		for (String vertice : this.grafo.keySet()) { // Añadimos todos los vertices, luego eliminamos el origen.
			restantes.add(vertice);
		}
		restantes.remove(origen);
		
		for (String vertice : restantes) {
			Double peso = getPeso(origen, vertice);
			/*
			 * Son adyacentes al origen.
			 */
			if (peso != null) {
				aristas.put(vertice, origen);
				pesos.put(vertice, peso);
				/*
				 * No son adyacentes al origen.
				 */
			} else {
				aristas.put(vertice, null);
				pesos.put(vertice, INFINITO);
			}

		}
		aristas.put(origen, origen);
		pesos.put(origen, 0.0);

		// Funcion de selección del algoritmo. Mientras queden vertices por valorar,
		// buscamos el de menor peso.
		while (!restantes.isEmpty()) {
			double min = INFINITO;
			desde = null;
			for (String v : restantes) {
				double peso = pesos.get(v);
				if (peso < min) {
					min = peso;
					desde = v;
				}
			}
			if (desde == null) // Se para el bucle si el vertice no es conexo.
				break;

			restantes.remove(desde);
			String aux = aristas.get(desde);
			resultado.add(new Arista(aux, desde, getPeso(aux, desde))); // Se agrega arista al resultado.

			for (String hasta : restantes) {
				Double peso = getPeso(desde, hasta);
				if (peso != null && peso < pesos.get(hasta)) {
					pesos.put(hasta, peso);
					aristas.put(hasta, desde);
				}
			}
		}

		long endNano = System.nanoTime();
		long endMili = System.currentTimeMillis();
		System.out.println("Tiempo de ejecución para algoritmo Prim: " + (endNano - startNano) + " nanosegundos. || "
				+ (endMili - startMili) + " milisegundos.");
		return resultado;

	}

	/**
	 * Algoritmo de Prim con cola de prioridad.
	 * @return resultado
	 */
	public List<Arista> primPQ() {
		long startNano = System.nanoTime();
		long startMili = System.currentTimeMillis();
		String origen = this.origen;
		/*
		 * Siempre que el origen sea nulo o que no se contenga la clave, muestra la
		 * siguiente excepción.
		 */
		if (origen == null || !this.grafo.containsKey(origen))
			throw new RuntimeException("No puede ser nulo.");
		
		HashSet<String> restantes = new HashSet<String>();
		PriorityQueue<Arista> colaDePrioridad = new PriorityQueue<Arista>(); //cola de prioridad de aristas
		List<Arista> resultado = new ArrayList<Arista>();
		String desde = origen;
		String hasta;
		Double peso;
		Arista aux;
		
		for (String vertice : this.grafo.keySet()) {
			restantes.add(vertice);
		}
		restantes.remove(origen);
		/*
		 * La siguiente parte equivale al núcleo principal del algoritmo.
		 */
		while (!restantes.isEmpty()) {
			for (Entry<String, Double> iterador : this.grafo.get(desde).entrySet()) {
				hasta = iterador.getKey();
				peso = iterador.getValue();
				if (restantes.contains(hasta)) {
					aux = new Arista(desde, hasta, peso);
					colaDePrioridad.add(aux);
				}
			}
			//Función de seleccion
			do {
				aux = colaDePrioridad.poll();
				desde = aux.getOrigen();
				hasta = aux.getDestino();
				peso = aux.getPeso();
			} while (!restantes.contains(hasta));

			restantes.remove(hasta);
			resultado.add(new Arista(aux.getOrigen(), aux.getDestino(), aux.getPeso()));
			desde = hasta;
		}
		long endNano = System.nanoTime();
		long endMili = System.currentTimeMillis();
		System.out.println("Tiempo de ejecución para algoritmo PrimPQ: " + (endNano - startNano) + " nanosegundos. || "
				+ (endMili - startMili) + " milisegundos.");
		return resultado;
	}

	
	/**
	 * Algoritmo de Kruskal. Dado un grafo conexo, no dirigido y ponderado, encuentra un arbol de recubrimiento minimo.
	 * @return resultado
	 */
	public List<Arista> kruskal() {
		long startNano = System.nanoTime();
		long startMili = System.currentTimeMillis();
		String origen = this.origen;
		
		if (origen == null || !this.grafo.containsKey(origen))
			throw new RuntimeException("No puede ser nulo.");
		
		TreeMap<String, String> aristas = new TreeMap<String, String>();
		TreeMap<String, Double> restantes = new TreeMap<String, Double>();
		List<Arista> resultadoFinal = new ArrayList<Arista>();

		for (String vertice : grafo.keySet()) { //Se introducen todos los vértices con infinito como peso
			restantes.put(vertice, INFINITO);
		}
		boolean esPrimero = true;
		while (!restantes.isEmpty()) {
			String keyMinima = restantes.firstKey();
			if (esPrimero) {
				keyMinima = origen;
				esPrimero = false;
			}
			
			//Función de selección. Se busca la arista mas cercana / de menor peso.
			Double valorMinimo = INFINITO;
			for (Entry<String, Double> entrada : restantes.entrySet()) {
				if (entrada.getValue() < valorMinimo) {
					valorMinimo = entrada.getValue();
					keyMinima = entrada.getKey();
				}
			}
			restantes.remove(keyMinima);
			
			//Para los vertices adyacentes
			for (Entry<String, Double> iterador : grafo.get(keyMinima).entrySet()) {
				String hasta = iterador.getKey();
				Double dActual = getPeso(aristas.get(hasta), hasta);
				dActual = dActual == null ? INFINITO : dActual;
				if (restantes.containsKey(hasta) && iterador.getValue() < INFINITO && iterador.getValue() < dActual) {
					restantes.put(hasta, iterador.getValue());
					aristas.put(hasta, keyMinima);

				}
			}
		}
		
		for (Entry<String, String> iterador : aristas.entrySet()) {
			String hasta = iterador.getKey();
			String desde = iterador.getValue();
			resultadoFinal.add(new Arista(hasta, desde, getPeso(hasta, desde)));
		}
		long endNano = System.nanoTime();
		long endMili = System.currentTimeMillis();
		System.out.println("Tiempo de ejecución para algoritmo Kruskal: " + (endNano - startNano) + " nanosegundos. || "
				+ (endMili - startMili) + " milisegundos.");
		return resultadoFinal;
	}

}
