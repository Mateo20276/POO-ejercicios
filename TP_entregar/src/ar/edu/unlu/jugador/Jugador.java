package ar.edu.unlu.jugador;

import java.util.ArrayList;

import ar.edu.unlu.baraja.*;

public class Jugador {
	
	private ArrayList<Carta> mano;
	
	private int puntos = 0;
	
	private String nombre;
	
	private boolean jodete = false;
		
	
	public Jugador( ArrayList<Carta> mano) {
		super();
		this.mano = new ArrayList<>();
		setMano(mano);
	}


	public Carta tirarCarta(int indice) {
		Carta carta = obtenerCarta(indice);
		eliminarCartaMano(indice);		
			
		return carta;
	}
	
	public Carta obtenerCarta(int indice) {
		Carta carta = this.mano.get(indice);
		return carta;
		
	}
	
	public boolean jodete() {
		boolean resultado = false;
		if (this.mano.size() == 1) {
			resultado = true;
		}
		
		return resultado;		
	}
	
	public boolean cantidadCartasCero() {
		boolean resultado = false;
		if (this.getCantidadCartas() == 0) {
			resultado = true;			
		}
		return resultado;
	}
	public void limpiarMazoJugador() {
		this.mano.clear();
	}
	
	public void sumarCarta(Carta carta) {
		this.mano.add(carta);
	
	}
	
	public int getCantidadCartas() {
		return mano.size();
	}
	
	private void eliminarCartaMano(int indice) {
		mano.remove(indice);	
	}
	

	public ArrayList<Carta> getMano() {
		return mano;
	}

	public void setMano(ArrayList<Carta> mano) {
		this.mano = mano;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setJodete(boolean jodete) {
		this.jodete = jodete;
	}


	public boolean getJodete() {
		return this.jodete;
	}
}
