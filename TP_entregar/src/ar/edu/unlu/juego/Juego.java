package ar.edu.unlu.juego;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unlu.baraja.*;
import ar.edu.unlu.jugador.*;
import ar.edu.unlu.observer.*;


public class Juego implements Observable {
	private static final int numeroMaximoJugadores = 6;
	
	private static final int numeroMinimoJugadores = 2;

	
	
	private List<Observador> observadores;

	private int jugadorInicial;
	
	private int jugadorActual;
	
	private int numeroJugadores;
	
	private ArrayList<Jugador> jugadores;
		
	private Mazo mazoArriba;
	
	private Mazo mazoAbajo;
	
	private int sentidoJuego = 1;
	
	
	public Juego() {
		super();
		setJugadorInicial(0);
		setJugadorActual(getJugadorInicial());
		this.observadores = new ArrayList<>();
		jugadores = new ArrayList<>();	
		mazoArriba = new Mazo();
		mazoAbajo = new Mazo();
		this.mazoArriba.cargarMazo();
		
	}

	private ArrayList<Mazo> repartirCartaJugador() { 
		ArrayList<Mazo> mazoJugadores = new ArrayList<>();
		int cont = 0;

		for (int i = 0; i < this.getNumeroJugadores(); i++) {
			mazoJugadores.add(new Mazo());
		}
		cont = 0;

		for (int i = 0; i < (this.getNumeroJugadores()*ReglasNumeroJugadores.cartasARepartir(this.getNumeroJugadores())); i++) {
			mazoJugadores.get(cont).agregarCarta(mazoArriba.getMazo().get(i));
			if ((cont + 1) == this.getNumeroJugadores() ) {
				cont = 0;			
			}
			else {cont++;}
		}
		
		for(int i = 0; i < this.getNumeroJugadores()*ReglasNumeroJugadores.cartasARepartir(this.getNumeroJugadores()); i++) {
			this.mazoArriba.eliminarCarta(0);
		}
		
		return mazoJugadores;
	}

	private void cargarJugadores() {
		ArrayList<Mazo> mazoJugadores = repartirCartaJugador();
		this.crearJugadores();
		for (int i = 0; i < this.getNumeroJugadores(); i++) {
			jugadores.get(i).setMano(mazoJugadores.get(i).getMazo());
		}		
	}
	private void crearJugadores() {
		for (int i = 0; i < this.getNumeroJugadores(); i++) {
			jugadores.add(new Jugador());
		}		
	}
	
	public boolean cantidadJugadores(int cant) {
		boolean veri = true;
		if ((cant >= numeroMinimoJugadores) && (cant <= numeroMaximoJugadores)) {
			this.setNumeroJugadores(cant);
			cargarJugadores();
			this.notificar(Eventos.CANTIDAD_JUGADORES);
		}
		else {
			veri = false;
			this.notificar(Eventos.CANTIDAD_JUGADORES_ERRONEA);
		}
		return veri;		
	}
	


	private boolean cartaArribaMazoAbajo(Carta carta) {
		boolean resultado = false;
		
		if(this.mazoAbajo.tamanioIgualCero()) {
			resultado = this.mazoAbajo.tamanioIgualCero();
		}
		
		else if (carta.getCambioJugador()) {
			if(carta.getNumero() == 4) {
				resultado = true;// TODO refactorizar
			}
		}

		else if ((this.mazoAbajo.obtenerUltimaCarta().getNumero() == carta.getNumero()) || (this.mazoAbajo.obtenerUltimaCarta()).getPalo() == carta.getPalo()) {
			if (!(carta.getcambioPalo() == null)){carta.setPalo(carta.getcambioPalo());carta.setcambioPalo(null);
				}
			resultado = true;// TODO refactorizar
		}
				
		return resultado;		
	}
	
	public ICarta tirarCarta(int indice) {
		Carta cartaAuxiliar = null;
		short opcion = '1';
		String opcionespecial = "0";
		boolean esCartavalida = (indice > 0)&&(indice < this.jugadores.get(this.getJugadorActual()).getCantidadCartas() + 1);
		if (esCartavalida) {// TODO al jugador
			opcion = '2';
			cartaAuxiliar = this.jugadores.get(this.getJugadorActual()).obtenerCarta(indice - 1);
			if (this.cartaArribaMazoAbajo(cartaAuxiliar)){
				this.jugadores.get(this.getJugadorActual()).tirarCarta(indice - 1);
				this.mazoAbajo.agregarCarta(cartaAuxiliar);
				opcion = '3';
			if (cartaAuxiliar.getNumero() == 12) {
				if (!(this.getNumeroJugadores() == 2)) {
					if (this.getSentidoJuego() == 1) {
						this.setSentidoJuego(-1);
						}
					else {this.setSentidoJuego(1);}
				}
				else {cartaAuxiliar.setCambioJugador(true);}
				opcionespecial = "12";

				}
			else if (cartaAuxiliar.getNumero() == 11) {
				opcionespecial = "11";
				
				}
			else if (cartaAuxiliar.getNumero() == 10) {
				opcionespecial = "10";
				}
			else if (cartaAuxiliar.getNumero() == 7) {
				opcionespecial = "7";
				}
			else if (cartaAuxiliar.getNumero() == 4) {//Logica???????
				cartaAuxiliar.setCambioJugador(true);
				opcionespecial = "4";			
				}
			}						
		}

		if (opcion == '1'){
			this.notificar(Eventos.CARTA_INEXISTENTE);
			}
		else if (opcion == '2'){
			this.notificar(Eventos.CARTA_NO_COINCIDENTE);
			cartaAuxiliar = null;								
		}
		else if (opcion == '3') {
			this.notificar(Eventos.CARTA_TIRADA_CORRECTAMENTE);
			
			if (opcionespecial == "0") {this.notificar(Eventos.CARTA_TIRADA_NORMAL);}
			
			else if (opcionespecial == "4") {this.notificar(Eventos.CARTA_ESPECIAL_4);}
			
			else if (opcionespecial == "7") {this.notificar(Eventos.CARTA_ESPECIAL_7);}
			
			else if (opcionespecial == "10") {this.notificar(Eventos.CARTA_ESPECIAL_10);}
			
			else if (opcionespecial == "11") {this.notificar(Eventos.CARTA_ESPECIAL_11);}
			
			else if (opcionespecial == "12") {this.notificar(Eventos.CARTA_ESPECIAL_12);}
			
		}	
		
		this.notificar(Eventos.CARTAS);
		
		return cartaAuxiliar;
	}
	
	public boolean terminaRonda() {
		boolean resultado = false;
		if (!(this.jugadores.get(this.getJugadorActual()).getJodete()) && (this.jugadores.get(this.getJugadorActual()).jodete())) {
			this.cantarJodete(); 
			}
		
		if (this.jugadores.get(this.getJugadorActual()).cantidadCartasCero()) {
			resultado = true;			
		}
		return resultado;
	}
	
	public void cambioJugadorInicial() {
		int jugadorIni;
		jugadorIni = this.jugadorInicial + 1;
		if (jugadorIni < this.getNumeroJugadores()) {setJugadorInicial(jugadorIni);}
		else {setJugadorInicial(0); jugadorIni = 0;}
		this.mazoArriba.limpiarMazo();
		this.mazoAbajo.limpiarMazo();
		for (Jugador jugador : this.jugadores) {
			jugador.limpiarManoJugador();
		}
		this.mazoArriba.cargarMazo();
		this.cargarJugadores();
		this.setSentidoJuego(1);
		this.setJugadorActual(this.getJugadorInicial());
		this.jugadores.get(jugadorActual).getMano().toString();
		this.notificar(Eventos.CAMBIAR_RONDA);	
		this.notificar(Eventos.CAMBIAR_JUGADOR);
		this.notificar(Eventos.CARTAS);
		
	}
	
	public void cambiojugadorActual(boolean cambio) {
		int jugadorAct;
		jugadorAct = this.getJugadorActual() + this.getSentidoJuego();
		this.jugadores.get(this.getJugadorActual()).setJodete(false);
		if (jugadorAct == -1) {setJugadorActual(this.getNumeroJugadores() - 1);}
		else if (jugadorAct < this.getNumeroJugadores()) {setJugadorActual(jugadorAct);}
		else {setJugadorActual(0);}
		if (!this.mazoAbajo.tamanioIgualCero()) {
//			if (this.mazoAbajo.obtenerUltimaCarta().getCambioJugador()) {this.mazoAbajo.obtenerUltimaCarta().setCambioJugador(false);this.cambiojugadorActual(false);}
			for (int i = 0; i <this.mazoAbajo.tamanioMazo(); i++) {
				if (this.mazoAbajo.obtenerCarta(i).getCambioJugador()) {
					this.mazoAbajo.obtenerCarta(i).setCambioJugador(false);
					this.cambiojugadorActual(false);
				}
			}
		}
		if (cambio) {this.notificar(Eventos.CAMBIAR_JUGADOR);this.notificar(Eventos.CARTAS);}		
	}

	public void cartaComodin10(String p) {
		this.mazoAbajo.obtenerUltimaCarta().setcambioPalo(this.mazoAbajo.obtenerUltimaCarta().cambiarUnPalo(p)); 
		this.notificar(Eventos.CAMBIO_COLOR);		
	}

	public String mostrarManoJugador() {
		String cadena = "";
		int i = 1;
		
		for (Carta carta : this.jugadores.get(getJugadorActual()).getMano()) {
			if (carta.getNumero() == 0) {
				cadena = cadena + (i++) + "-" + "   COMODIN" + "\n" ;
			}
			else {cadena =cadena + (i++) +" - " + carta.toString() + "\n";}
		}
		return cadena;
	}
	
	public String mostrarCartaMazoAbajo() {
		String carta = "";
		if(!(this.mazoAbajo.tamanioIgualCero())) {
			carta = "Carta boca arriba: " + this.mazoAbajo.obtenerUltimaCarta().toString();}
		return (carta);
	}
	
	public void pasarJugador() {
	if (this.terminaRonda()) {
		this.cambioJugadorInicial();
	}
	else{this.cambiojugadorActual(true);}		
	}
	
	
	public boolean  robarCarta(boolean mostrar) {
		boolean resultado = true;
	
		if(this.mazoArriba.tamanioIgualCero()) {
			if (!this.mazoAbajo.tamanioIgualCero()) {
				this.mazoArriba.pasarCartasDeUnMazo(mazoAbajo);
			}
			else {resultado = false;
				  this.notificar(Eventos.NO_ES_POSIBLE_ROBAR_CARTAS);
			}	
		}			
		
		if (resultado){
			this.jugadores.get(this.getJugadorActual()).sumarCarta(this.mazoArriba.getMazo().get(0));
			this.mazoArriba.eliminarCarta(0);	
		}
		
		if (mostrar) {this.notificar(Eventos.CARTAS);}
		return resultado;		
	}
	
	public void cargarNombreJugadores(String nombre, int i) {
			this.jugadores.get(i).setNombre(nombre);			
			if ((i+1) == this.numeroJugadores) {
				this.notificar(Eventos.COMIENZA_EL_JUEGO);
				this.notificar(Eventos.JUGADOR_INICIAL);
				this.notificar(Eventos.CARTAS);
			}	
	}
	
	public void cantarJodete() {
		for (int i = 0; i < 5; i++) {
			if (!this.robarCarta(false)) {
				break;													
			}	
		}
		this.notificar(Eventos.CARTAS);
	}
	
	public void cantoJodete() {
		if (this.jugadores.get(this.jugadorActual).jodete()) {
			this.jugadores.get(this.jugadorActual).setJodete(true);			
		}
		else {cantarJodete();}		
	}

	public String getNombreJugadorActual() {
		return this.jugadores.get(jugadorActual).getNombre();
	}

	public Mazo getMazoAbajo() {
		return mazoAbajo;
	}
		
	public int getJugadorInicial() {
		return jugadorInicial;
	}

	public void setJugadorInicial(int jugaroInicial) {
		this.jugadorInicial = jugaroInicial;
	}

	private int getJugadorActual() {
		return jugadorActual;
	}

	private void setJugadorActual(int jugadorActual) {
		this.jugadorActual = jugadorActual;
	}
	
	private void setNumeroJugadores(int cant) {
		this.numeroJugadores = cant;
		
	}

	public void agregadorObservador(Observador observador) {
		this.observadores.add(observador);
	}

	
	public void notificar(Object evento) {
		for (Observador observador : this.observadores) {
			observador.actualizar(evento, this);
		}
	}

	public int getSentidoJuego() {
		return sentidoJuego;
	}

	private void setSentidoJuego(int sentidoJuego) {
		this.sentidoJuego = sentidoJuego;
	}
	
	public int getNumeroJugadores() {
		return numeroJugadores;
	}

}
