package org.eda2.practica04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TSP {

	
	private boolean directed;
	private HashMap<String, HashMap<String, Double>> graph;// Key=Vertice, Key2=, Vertice, Value=Peso
	private String origin;
	protected double minEdgeValue;

	/**
	 * Constructor por defecto
	 * 
	 */
	public TSP() {
		this.directed = true;
		this.graph = new HashMap<String, HashMap<String, Double>>();
	}

	/**
	 * Constructor al que se le pasa por parametro el archivo de datos y un origen.
	 * @param file - archivo
	 * @param origin - origen
	 */
	public TSP(File file, String... origin) {

		this();
		try {
			Scanner sc = new Scanner(file);
			int nVertex = -1;
			int nEdges = -1;
			String[] tokens;
			if (sc.hasNext()) {
				setDirected(sc.nextLine().equals("1"));
			}
			if (sc.hasNext()) {
				nVertex = Integer.parseInt(sc.nextLine().trim());
			}
			for (int i = 0; i < nVertex; i++) {
				addVertex(sc.nextLine());
			}
			if (sc.hasNext()) {
				nEdges = Integer.parseInt(sc.nextLine().trim());
			}
			for (int i = 0; i < nEdges; i++) {
				tokens = sc.nextLine().trim().split("[ ]+");
				if (tokens.length != 3)
					continue;
				addEdge(tokens[0], tokens[1], Double.parseDouble(tokens[2]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (origin == null || origin.length == 0) {
			List<String> listaAux = new ArrayList<String>(graph.keySet());
			Collections.shuffle(listaAux);
			this.origin = listaAux.get(0);
		} else {
			this.origin = origin[0];
		}
	}

	/**
	 * Devuelve el grafo.
	 * @return
	 */
	public HashMap<String, HashMap<String, Double>> getGraph() {
		return graph;
	}

	/**
	 * Obtiene el origen
	 * 
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Establece el origen
	 * 
	 * @param origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Devuelve si el grafo es o no dirigido.
	 */
	public boolean getDirected() {
		return this.directed;
	}

	/**
	 * Establece si el grafo es dirigido.
	 * 
	 * @param directed
	 */
	public void setDirected(boolean directed) {
		this.directed = directed;
	}

	/**
	 * Devuelve true o false dependiendo de si el grafo esta vacío.
	 * 
	 */
	public boolean isEmpty() {
		return this.graph.isEmpty();
	}

	/**
	 * Elimina los elementos del grafo.
	 */
	public void clear() {
		this.graph.clear();
	}

	/**
	 * Devuelve el número de vértices
	 * 
	 */
	public int numberOfVertex() {
		return this.graph.size();
	}

	/**
	 * Devuelve el número de aristas.
	 * 
	 */
	public int numberOfEdges() {
		int count = 0;
		for (HashMap<String, Double> it : this.graph.values())
			count += it.size();
		return directed ? count : count / 2;
	}

	/**
	 * Añade un vertice.
	 * 
	 * @param v - vertice
	 */
	public boolean addVertex(String v) {
		if (this.graph.containsKey(v))
			return false;
		this.graph.put(v, new HashMap<String, Double>());
		return true;
	}

	/**
	 * Añade una arista.
	 * 
	 * @param v1 - vertice1
	 * @param v2 - vertice2
	 * @param w - peso
	 */
	public boolean addEdge(String v1, String v2, double w) {
		addVertex(v1);
		addVertex(v2);
		this.graph.get(v1).put(v2, w);
		if (!this.directed) {
			this.graph.get(v2).put(v1, w);
		}
		return true;
	}

	/**
	 * Método utilizado para saber si el grafo contiene una determinada arista.
	 * 
	 * @param v1 - vertice1
	 * @param v2 - vertice2
	 * @return true/false
	 */
	public boolean containsEdge(String v1, String v2) {
		return this.graph.containsKey(v1) && this.graph.get(v1).containsKey(v2);
	}

	/**
	 * Obtiene el peso de una arista.
	 * @param v1 - vertice1
	 * @param v2 - vertice2
	 */
	public Double getWeight(String v1, String v2) {
		return containsEdge(v1, v2) ? this.graph.get(v1).get(v2) : null;
	}

	/**
	 * Establece un vértice.
	 */
	public HashSet<String> vertexSet() {
		return new HashSet<String>(this.graph.keySet());
	}

	/**
	 * Obtiene los vecinos
	 * @param v - vertice
	 */
	public HashSet<String> getNeighbors(String v) {
		HashMap<String, Double> neighbors = this.graph.get(v);
		return neighbors == null ? null : new HashSet<String>(neighbors.keySet());
	}
	
	

	/**
	 * Método público Backtracking
	 * 
	 * @return results
	 */
	public ArrayList<ArrayList<String>> BackTracking() {
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		String[] stage = new String[numberOfVertex() + 1];
		int level = 1;

		initStructures(visited, stage);
		System.out.println("VISITED: " + visited);
		BackTracking(stage, level, visited, results);
		return results;
	}

	/**
	 * Método para inicializar las estructuras de visitados y etapas.
	 * 
	 * @param visited - Estructura de visitados
	 * @param stage   - Estructura de etapas
	 */
	private void initStructures(HashMap<String, Boolean> visited, String[] stage) {
		for (String v : graph.keySet()) {
			visited.put(v, false);
		}
		stage[0] = origin;
		stage[stage.length - 1] = origin;
		visited.put(origin, true);
	}

	/**
	 * Método privado recursivo para Backtracking.
	 * 
	 * @param etapa
	 * @param nivel
	 * @param visitados
	 * @param resultados
	 */
	private void BackTracking(String[] stage, int level, HashMap<String, Boolean> visited, ArrayList<ArrayList<String>> results) {
	    for (Entry <String, Boolean> it : visited.entrySet()) { //SeleccionarNuevaOpcion hasta Ultima opción
	        if(it.getValue()) continue; //Ya está visitado
	        if(esAceptable(stage, level, it.getKey())) { //Si Aceptable
	            stage[level] = it.getKey(); //Anotar opcion
	            if(level == visited.size()-1) { //Si solución completa -> Incluir solucion
	            	ArrayList<String> temp = new ArrayList<String>(Arrays.asList(stage));
	            	double suma = 0;
	            	for(int i = 1; i < temp.size(); i++) {
	            		double peso = getWeight(temp.get(i - 1), temp.get(i));
	            		suma += peso;
	            	}
	            	temp.add("Coste: " + String.valueOf(suma));
	            	results.add(temp);
	            }else {//Si solucion incompleta -> Etapa siguiente
	                visited.put(it.getKey(), true);
	                BackTracking(stage, level+1, visited, results);
	                visited.put(it.getKey(), false);
	            }
	        }
	    }
	    stage[level] = null; //Marcar como no explorado
	}

	

	/**
	 * Método esAceptable.
	 * 
	 * @param etapa
	 * @param nivel
	 * @param candidato
	 * @param visited
	 * @return true/false
	 */
	private boolean esAceptable(String[] stage, int level, String candidate) {
	    boolean esAlcanzable = containsEdge(stage[level-1], candidate);
	    boolean esUltimoAlcanzable = true;
	    if(level == stage.length - 2) {
	    	esUltimoAlcanzable = containsEdge(candidate, stage[stage.length-1]);
	    }
	    return esAlcanzable && esUltimoAlcanzable;
	}

	
	/**
	 * Devuelve el valor minimo de la arista.
	 * @return minimum - valor mínimo
	 */
	public double minimumEdgeValue() {
		double minimum = Double.MAX_VALUE;
		for (HashMap<String, Double> it : this.graph.values()) {
			for(Double value : it.values()) {
				if(value<minimum) {
					minimum = value;
				}
			}
		}
		return minimum;
	}

	/**
	 * Algoritmo Branch and Bound para el TSP
	 * @param source
	 */
	public ArrayList<String> TSPBaB(String source) {
		if (graph.get(source) == null)
			return null;

		PriorityQueue<PathNode> priorityQueue = new PriorityQueue<PathNode>();
		ArrayList<String> shortestCircuit = null; //El mejor camino encontrado
		int nVertices = numberOfVertex();
		minEdgeValue = minimumEdgeValue();
		double bestCost = Double.MAX_VALUE;
		PathNode firstNode = new PathNode(source); // Constructor de clase PathNode
		priorityQueue.add(firstNode);

		while(priorityQueue.size() > 0) {
			PathNode Y = priorityQueue.poll(); // Y = menorElemento de la cola de prioridad en funcion de 'estimatedCost'
			if (Y.getEstimatedCost() >= bestCost) break;
			String from = Y.lastVertexRes();
				// Si el numero de vertices visitados es n
				// y existe una arista que conecte 'from' con source
				if ((Y.getVisitedVertices() == numberOfVertex()) && (containsEdge(from, source))) {
					Y.addVertexRes(source);// Actualizar 'res' en Y añadiendo el vertice 'source'
					Y.totalCost += getWeight(from,  origin);// Actualizar 'totalCost' en Y.
					if (Y.getTotalCost() < bestCost) {
						bestCost = Y.getTotalCost();
						shortestCircuit = new ArrayList<String>(Y.getRes());
					}
				} else {
					for(String to : getNeighbors(from)){ // Iterar para todos los vertices adyacentes a from.
						if (!Y.isVertexVisited(to)) { //Si el vertice 'to' todavia no ha sido visitado en Y.
							PathNode X = new PathNode(Y); // Creamos un pathnode nuevo 'X' (copia de 'Y').
							X.addVertexRes(to);// Anadir 'to' a 'res' en X
							X.visitedVertex++; // Incrementar en 1 los vertices visitados en X
							X.totalCost += getWeight(from, to);// Actualizar 'totalCost' en X.
							X.estimatedCost = X.totalCost + (nVertices - X.getVisitedVertices() + 1) * minEdgeValue; // Actualizar 'estimatedCost' en X.
							if (X.getEstimatedCost() < bestCost) {
								priorityQueue.add(X);
							}
						}
					}
				}
			}
		return shortestCircuit;
	}

}
