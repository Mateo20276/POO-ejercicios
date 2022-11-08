package ar.edu.unlu.juego;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unlu.baraja.*;
import ar.edu.unlu.jugador.*;
import ar.edu.unlu.observer.*;

public class Juego implements Observable {
	private List<Observador> observadores;
	
	private int jugadorInicial;
	
	private int jugadorActual;
	
	private int numeroJugadores;
	
	private ArrayList<Jugador> jugadores;
	
	private int[] cartasEspeciales1 = {2,4,7,10,11,12,0};
	
	private Mazo mazoArriba;
	
	private Mazo mazoAbajo;
	
	
	
	public Juego(int numeroJugadores) {
		super();
		setJugadorInicial(0);
		setJugadorActual(getJugadorInicial());
		this.numeroJugadores = numeroJugadores;
		this.observadores = new ArrayList<>();
		jugadores = new ArrayList<>();		
		Mazo mazoarriba = new Mazo();
		mazoarriba.generarMazo();
		mazoarriba.mezclar();
		setMazoArriba(mazoarriba);
		Mazo mazoabajo = new Mazo();
		setMazoAbajo(mazoabajo);
		cargarJugadores();
		
	}



	private ArrayList<Mazo> repartirCartaJugador(Integer numero) {  // falta funcion de agregar una carta en especi
		ArrayList<Mazo> mazoJugadores = new ArrayList<>();//ver si esto funciona
		int cont = 0;
		
		for (int i = 0; i < numero; i++) {
			mazoJugadores.add(new Mazo());
		}
		for (int i = 0; i < (numero*ReglasNumeroJugadores.cartasARepartir(this.numeroJugadores)); i++) {
			mazoJugadores.get(cont).agregarCarta(mazoArriba.getMazo().get(i));
			if ((cont + 1) == numero ) {
				cont = 0;			
			}
			else {cont++;}
		}
		
		return mazoJugadores;
	}

	private void cargarJugadores() {
		for (int i = 0; i < this.numeroJugadores; i++) {
			jugadores.add(new Jugador(repartirCartaJugador(this.numeroJugadores).get(i).getMazo()));
		}

	}
	
	public void tirarCarta(int indice) {
		Carta cartaAuxiliar;
		cartaAuxiliar = this.jugadores.get(this.jugadorActual).tirarCarta(indice - 1);
		this.mazoAbajo.agregarCarta(cartaAuxiliar);
		cambiojugadorActual();
	}
	
	public boolean terminaRonda() {
		if (this.jugadores.get(this.jugadorActual).getCantidadCartas() == 0) {
			return true;
		}
		return false;
	}
	
	public void cambioJugadorInicial() {
		int jugadorIni;
		jugadorIni = this.jugadorInicial + 1;
		if (jugadorIni < this.numeroJugadores) {setJugadorInicial(jugadorIni);}
		else {setJugadorInicial(0);}
		
	}
	
	private void cambiojugadorActual() {
		int jugadorAct;
		jugadorAct = this.jugadorActual + 1;
		if (jugadorAct < this.numeroJugadores) {setJugadorActual(jugadorAct);}
		else {setJugadorActual(0);}
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
	
	
	public int getJugadorInicial() {
		return jugadorInicial;
	}



	public void setJugadorInicial(int jugaroInicial) {
		this.jugadorInicial = jugaroInicial;
	}



	public int getJugadorActual() {
		return jugadorActual;
	}



	public void setJugadorActual(int jugadorActual) {
		this.jugadorActual = jugadorActual;
	}



	@Override
	public void agregadorObservador(Observador observador) {
		this.observadores.add(observador);
	}



	@Override
	public void notificar(Object evento) {
		// TODO Auto-generated method stub
		
	}



	
}
