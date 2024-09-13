package ar.edu.unlu.jugador;
import java.io.Serializable;
import java.util.ArrayList;

import ar.edu.unlu.baraja.*;

public class Jugador implements Serializable {

	private static final long serialVersionUID = -5912526369939190900L;

	private ArrayList<Carta> mano;
	
	private int puntos = 0;
	
	private String nombre;
	
	private boolean jodete = false;

	private boolean perdio = false;
		
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
		return (this.mano.size() == 1);
	}

	
	public boolean cantidadCartasCero() {
		return (this.getCantidadCartas() == 0);
	}
	
	public boolean esCartaValida(int indice) {		
		return ((indice > 0)&&(indice < (this.getCantidadCartas() + 1)));
	};
	
	public void limpiarManoJugador() {
		this.mano.clear();
	}

	public String toString() {
		return "Jugador [mano=" + mano + "]";
	}

	public void SumarPuntos(Integer x) {
		for (Integer i = 0; i < this.getCantidadCartas(); i++) {
			Integer numero = mano.get(i).getNumero();
			if ((numero == 10) || (numero == 0)){
				setPuntos(25 * x + getPuntos());
			}
			else {setPuntos(x + getPuntos());}
		}
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
	
	public void setPerdio(boolean r) {
		this.perdio = r;
	}
	
	public boolean getPerdio() {
		return this.perdio;
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
