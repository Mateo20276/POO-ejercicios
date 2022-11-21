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
	
	public boolean  tirarCarta(int indice) {
		boolean resultado =  this.modelo.tirarCarta(indice);
		if (this.modelo.terminaRonda()) {
			this.modelo.cambioJugadorInicial();
		}
		return resultado;
	}
	
	
	
	
	
	@Override
	public void actualizar(Object evento, Observable observado) {

		if(evento instanceof Eventos) {
			switch((Eventos) evento) {
			case CARTA_INEXISTENTE:
				this.vista.mostrarCartaInexistente();
				break;
				
			case CARTA_NO_COINCIDENTE:
				this.vista.mostrarCartaNoCoincidente();
				break;
			
			
			}
		}
	}

	public String verCartas() {
		return this.modelo.mostrarManoJugador();
		
	}

	public void pasarJugador() {
		this.modelo.cambiojugadorActual();
		
	}

	public boolean cantidadJugadores(int cant) {
		return this.modelo.cantidadJugadores(cant);
		
	}

		
}
	
	
	


