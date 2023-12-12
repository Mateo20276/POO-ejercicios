package ar.edu.unlu.jugador;

import java.util.ArrayList;

import ar.edu.unlu.baraja.*;

public class Jugador {
	
	private ArrayList<Carta> mano;
	
	private int puntos = 0;
	
	private String nombre;
	
	private boolean jodete = false;
		
	
	public Jugador() {
		super();
		this.mano = new ArrayList<>();
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
/*		boolean resultado = this.mano.size() == 1;
		if (this.mano.size() == 1) {
			resultado = true;
		}
		
		return resultado;		*/
		return (this.mano.size() == 1);
	}
	
	public boolean cantidadCartasCero() {
		/*boolean resultado = false;
		if (this.getCantidadCartas() == 0) {
			resultado = true;			
		}
		return resultado;*/
		return (this.getCantidadCartas() == 0);
	}
	
	public boolean esCartaValida(int indice) {		
		return ((indice > 0)&&(indice < (this.getCantidadCartas() + 1)));
	};
	
	public void limpiarManoJugador() {
		this.mano.clear();
	}
	
	@Override
	public String toString() {
		return "Jugador [mano=" + mano + "]";
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
