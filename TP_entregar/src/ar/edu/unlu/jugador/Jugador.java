package ar.edu.unlu.jugador;

import java.util.ArrayList;

import ar.edu.unlu.baraja.*;

public class Jugador {
	
	private ArrayList<Carta> mano;
	
	private int puntos = 0;
		
	
	public Jugador( ArrayList<Carta> mano) {
		super();
		this.mano = new ArrayList<>();
		setMano(mano);
	}


	public Carta tirarCarta(int indice) {
		Carta carta = mano.get(indice);
		eliminarCartaMano(indice);		
		
		
		return carta;
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
}
