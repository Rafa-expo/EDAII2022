package org.eda2.practica01;

import java.util.ArrayList;

public class Player{

    private String playerName;
    private ArrayList<String> teams;
    private ArrayList<String> positions;
    private int score;

    /**
     * Constructor
     * @param playerName - nombre del jugador
     * @param team -  equipo al que pertenece
     * @param position - posicion del jugador
     * @param score - puntuacion del jugador
     */
    public Player(String playerName, String team, String position, int score){
        this.playerName = playerName;
        this.teams = new ArrayList<String>();
        this.teams.add(team);
        this.positions = new ArrayList<String>();
        this.positions.add(position);
        this.score = score;
    }
    
    /**
     * Metodo para añadir jugadores.
     * @param team - equipo del jugador
     * @param position - posicion del jugador
     * @param score - puntuacion del jugador
     */
    public void actualizar(String team, String position, int score) {
    	if(score <= 0) return;
    	this.teams.add(team);
    	this.positions.add(position);
    	this.score += score;
    }

    /**
     * Obtiene el nombre del jugador.
     * @return nombre
     */
    public String getPlayerName(){
        return this.playerName;
    }
    
    /**
     * Establece el nombre del jugador
     * @param playerName - nombre
     */
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    /**
     * Devuelve la estructura teams.
     * @return teams
     */
    public ArrayList<String> getTeams(){
        return this.teams;
    }
    
    /**
     * Inicializa la estructura teams.
     * @param teams
     */
    public void setTeams(ArrayList<String> teams){
        this.teams = teams;
    }

    /**
     * Devuelve la posicion del jugador.
     * @return position
     */
    public ArrayList<String> getPositions(){
        return this.positions;
    }
    
    /**
     * Establece la posicion.
     * @param positions
     */
    public void setPositions(ArrayList<String> positions){
        this.positions = positions;
    }

    /**
     * Devuelve la puntuacion media del jugador.
     */
    public int getScore(){
        return this.score/this.teams.size();
    }
    
    /**
     * Establece un score.
     * @param score
     */
    public void setScore(int score){
        this.score = score;
    }
    
    /**
     * Metodo toString para mostrar el nombre del jugador y su puntuacion.
     */
    public String toString() {
    	return this.playerName + ": " +getScore() + " puntos";
    }
    

}