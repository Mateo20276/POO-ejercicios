package ar.edu.unlu.juego;
import java.util.ArrayList;
import ar.edu.unlu.baraja.*;
import ar.edu.unlu.jugador.*;


public class Juego {
	private int numeroJugadores;
	
	private ArrayList<Jugador> jugadores;
	
	private int[] cartasEspeciales1 = {2,4,7,10,11,12,0};
	
	private Mazo mazoArriba;
	
	private Mazo mazoAbajo;
	
	
	
	public Juego(int numeroJugadores, ArrayList<Jugador> jugadores,Mazo mazoArriba, Mazo mazoAbajo) {
		super();
		this.numeroJugadores = numeroJugadores;
		jugadores = new ArrayList<>();
		cargarJugadores();
		
	}



	private ArrayList<Mazo> repartirCartaJugador(Integer numero) {  // falta funcion de agregar una carta en especi
		ArrayList<Mazo> mazoJugadores = new ArrayList<>();//ver si esto funciona
		int cont = 0;
		
		for (int i = 0; i < numero; i++) {
			mazoJugadores.add(new Mazo());
		}
		for (int i = 0; i < (numero*ReglasNumeroJugadores.cartasARepartir(this.numeroJugadores)); i++) {
			mazoJugadores.get(cont).agregarCarta(mazoArriba.getMazo().get(cont));
			if (((cont + 1) % numero )< numero) {
				cont = 0;
			}
		}
		
		return mazoJugadores;
	}

	private void cargarJugadores() {
		for (int i = 0; i < this.numeroJugadores; i++) {
			jugadores.add(new Jugador(repartirCartaJugador(this.numeroJugadores).get(i).getMazo()));
		}

	}

	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}



	public void setJugadores(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}


	public Mazo getMazoArriba() {
		return mazoArriba;
	}



	public void setMazoArriba(Mazo mazoArriba) {
		this.mazoArriba = mazoArriba;
	}



	public Mazo getMazoAbajo() {
		return mazoAbajo;
	}



	public void setMazoAbajo(Mazo mazoAbajo) {
		this.mazoAbajo = mazoAbajo;
	}
	
	
}
