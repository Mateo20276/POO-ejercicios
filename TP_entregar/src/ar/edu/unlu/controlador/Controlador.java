package ar.edu.unlu.controlador;

import ar.edu.unlu.juego.*;
import ar.edu.unlu.jugador.*;
import ar.edu.unlu.observer.*;
import ar.edu.unlu.vista.VistaConsola;


public class Controlador implements Observador {
	
	private VistaConsola vista;

	private Juego modelo;
	
	public Controlador(Juego juego, VistaConsola vista){
		this.modelo = juego;
		this.vista = vista;
		this.vista.setControlador(this);
		this.modelo.agregadorObservador(this);

	}
	
	public void tirarCarta(int indice) {
		this.modelo.tirarCarta(indice);
		if (this.modelo.terminaRonda()) {
			this.modelo.cambioJugadorInicial();
		}
	}
	
	
	
	
	
	@Override
	public void actualizar(Object evento, Observable observado) {

		
	}
	
	
	

}
