package org.eda2.practica3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TestPractica03 {

	@Test
	void testMochilaClasicaP01() {

		Mochila mochila = new Mochila("p01");

		ArrayList<Objeto> sol = mochila.mochila();

		assertEquals(sol.get(0).toString(), "Objeto4, peso=44.0, valor=68.0");
	}

	@Test
	void testMochilaIlimitadaP02() {

		Mochila mochila = new Mochila("p02");

		ArrayList<Objeto> sol = mochila.mochilaIlimitada();

		assertEquals(sol.get(0).toString(), "Objeto3, peso=11.0, valor=23.0");
	}


	@Test
	void testMochilaClasicaP02() {

		Mochila mochila = new Mochila("p02");

		ArrayList<Objeto> sol = mochila.mochila();

		assertNotEquals(sol.get(0).toString(), "objeto3, peso=11.0, valor=23.0");
	}

	@Test
	void testMochilaGreedyP02() {

		Mochila mochila = new Mochila("p02");

		ArrayList<Objeto> sol = mochila.mochilaGreedy();

		assertNotEquals(sol.get(0).toString(), "objeto1, peso=41.0, valor=442.0, cantidad=1.0");
	}



}
