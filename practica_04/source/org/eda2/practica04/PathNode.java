package org.eda2.practica04;

import java.util.ArrayList;
import java.util.List;

public class PathNode extends TSP implements Comparable<PathNode> {

	private ArrayList<String> res;
	public int visitedVertex;
	public double totalCost;
	public double estimatedCost;

	/**
	 * Constructor
	 * @param vertexToVisit
	 */
	public PathNode(String vertexToVisit) {
		res = new ArrayList();
		res.add(vertexToVisit);
		visitedVertex = 1;
		totalCost = 0.0;
		estimatedCost = numberOfVertex() * minEdgeValue;
	}
	
	/**
	 * Constructor copia
	 * @param parentPathNode
	 */

	public PathNode(PathNode parentPathNode) {
		this.res = new ArrayList(parentPathNode.res);
		this.visitedVertex = parentPathNode.visitedVertex;
		this.totalCost = parentPathNode.totalCost;
		this.estimatedCost = parentPathNode.estimatedCost;
	}


	/**
	 * Compara los costes estimados.
	 */
	@Override
	public int compareTo(PathNode p) {
		return Double.compare(this.estimatedCost, p.estimatedCost);
	}

	public ArrayList getRes() {
		return this.res;
	}

	/**
	 * Añade un vértice.
	 * @param v - vértice
	 */
	public void addVertexRes(String v) {
		this.res.add(v);
	}

	/**
	 * Obtiene el último vértice.
	 */
	public String lastVertexRes() {
		return this.res.get(this.res.size() - 1);
	}

	/**
	 * Indica si un vértice ha sido o no visitado.
	 * @param v
	 * @return
	 */
	public boolean isVertexVisited(String v) {
		return this.res.contains(v);
	}

	/**
	 * Obtiene los vertices visitados.
	 * @return
	 */
	public int getVisitedVertices() {
		return visitedVertex;
	}

	/**
	 * Establece los vértices visitados.
	 * @param visitedVertices
	 */
	public void setVisitedVertices(int visitedVertices) {
		this.visitedVertex = visitedVertices;
	}

	/**
	 * Obtiene el coste total.
	 */
	public double getTotalCost() {
		return this.totalCost;
	}

	/**
	 * Establece el coste total.
	 * @param totalCost
	 */
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * Obtiene el coste estimado.
	 * @return
	 */
	public double getEstimatedCost() {
		return estimatedCost;
	}

	/**
	 * Establece el coste estimado.
	 * @param estimatedCost
	 */
	public void setEstimatedCost(double estimatedCost) {
		this.estimatedCost = estimatedCost;
	}

	
}
