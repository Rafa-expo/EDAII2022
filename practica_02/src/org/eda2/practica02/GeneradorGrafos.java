package org.eda2.practica02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class GeneradorGrafos {

	private static String ruta = System.getProperty("user.dir") + File.separator + "src" + File.separator + "org" +  
			File.separator + "eda2" + File.separator + "practica02" + File.separator;
	private static ArrayList<String> temp = new ArrayList<String>();
	public static void main(String[] args) throws FileNotFoundException {
		long startNano = System.nanoTime();
		long startMili = System.currentTimeMillis();
		grafoAleatorio(1000,"Datos02.txt");
		long endNano = System.nanoTime();
		long endMili = System.currentTimeMillis();
		System.out.println("Tiempo de ejecución: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
	}
	
	private static void grafoAleatorio(int nVertices, String nombreArchivo) throws FileNotFoundException {
		
		Random random = new Random();
		PrintWriter pw = new PrintWriter (new File (ruta + nombreArchivo));
		
		pw.println("0"); //0 = no dirigido
		pw.println(nVertices);
		
		ArrayList<AristaGenerador> aux = new ArrayList<AristaGenerador>();
		
		int nAristas = nVertices*(random.nextInt(2)+2);
		ArrayList<AristaGenerador> resultadoFinal = new ArrayList<AristaGenerador>(nAristas);
		
		for (int i = 0; i < nVertices; i++) {
			pw.println(i);
		}
		
		for (int i = 0; i < nVertices; i++) {
			for (int j = i+1; j < nVertices; j++) {
				int pesoAleatorio = (int) (Math.random()*100+1);
				if(i+1 == j) {//Si son numeros consecutivos, se agrega
					resultadoFinal.add(new AristaGenerador (i, j, pesoAleatorio));
					temp.add(i + " " + j);
				}else {//Si no, pasan a la lista auxiliar de aristas restantes
					aux.add(new AristaGenerador(i, j, pesoAleatorio));
				}
			}
		}
		
		pw.println(nAristas);
		
		int elementosAristas = 0;
		while(elementosAristas < resultadoFinal.size() && resultadoFinal.size() < nAristas) {
			int indiceAleatorio = random.nextInt(aux.size());
			while (contieneArista(resultadoFinal, aux.get(indiceAleatorio))) {
				indiceAleatorio = random.nextInt(aux.size());
			} 
			
			resultadoFinal.add(aux.get(indiceAleatorio));
			elementosAristas++;
		}
		
		for (AristaGenerador a : resultadoFinal) {
			pw.println(a);
		}
		
		pw.close();
	}
	
	/*
	 * Este método es creado para comprobar que no se contenga la arista en una lista correspondiente.
	 */
	public static boolean contieneArista(ArrayList<AristaGenerador> c, AristaGenerador a ) {
		String str1 = a.getOrigen() + " " + a.getDestino();
		String str2 = a.getDestino() + " " + a.getOrigen();
		if(temp.contains(str1) || temp.contains(str2)) {
			temp.add(str1);
			return true;
		} else {
			temp.add(str1);
		    return false;
		}
	}	
}
