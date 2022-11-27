package ar.edu.unlu.juego;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unlu.baraja.*;
import ar.edu.unlu.jugador.*;
import ar.edu.unlu.observer.*;


public class Juego implements Observable {
	private final int numeroMaximoJugadores = 6;
	
	private final int numeroMinimoJugadores = 2;
	
	private Palo paloCarta10 = null;
	
	
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
		this.generarMazoArriba();
		
	}





	private ArrayList<Mazo> repartirCartaJugador(Integer numero) { 
		ArrayList<Mazo> mazoJugadores = new ArrayList<>();
		int cont = 0;

		for (int i = 0; i < numero; i++) {
			mazoJugadores.add(new Mazo());
		}
		cont = 0;

		for (int i = 0; i < (numero*ReglasNumeroJugadores.cartasARepartir(this.numeroJugadores)); i++) {
			mazoJugadores.get(cont).agregarCarta(mazoArriba.getMazo().get(i));
			if ((cont + 1) == numero ) {
				cont = 0;			
			}
			else {cont++;}
		}
		
		for(int i = 0; i < numero*ReglasNumeroJugadores.cartasARepartir(this.numeroJugadores); i++) {
			this.mazoArriba.eliminarCarta(0);
		}
		
		return mazoJugadores;
	}

	private void cargarJugadores() {
		ArrayList<Mazo> mazoJugadores = repartirCartaJugador(this.numeroJugadores);
		for (int i = 0; i < this.numeroJugadores; i++) {
			jugadores.add(new Jugador(mazoJugadores.get(i).getMazo()));
		}

	}
	
	public boolean cantidadJugadores(int cant) {
		boolean veri = true;
		if ((cant >= numeroMinimoJugadores) && (cant <= numeroMaximoJugadores)) {
			this.numeroJugadores = cant;
			cargarJugadores();
			this.notificar(Eventos.CANTIDAD_JUGADORES);
		}
		else {
			veri = false;
		}
		return veri;
		
	}
	
	private boolean cartaArribaMazoAbajo(Carta carta) {
		boolean resultado = false;
		
		if(this.mazoAbajo.tamanioIgualCero()) {
			resultado = true;
		}

		else if ((this.mazoAbajo.obtenerUltimaCarta().getNumero() == carta.getNumero()) || (this.mazoAbajo.obtenerUltimaCarta()).getPalo() == carta.getPalo()) {
			if (!(this.paloCarta10 == null)){this.setPaloCarta10(null);}
			resultado = true;
		}
		
		
		
		return resultado;
		
	}
	
	public Carta tirarCarta(int indice) {
		Carta cartaAuxiliar = null;
		short opcion = '1';
		String opcionespecial = "0";
		if ((indice > 0)&&(indice < this.jugadores.get(this.jugadorActual).getCantidadCartas() + 1)) {
			opcion = '2';
			cartaAuxiliar = this.jugadores.get(this.jugadorActual).obtenerCarta(indice - 1);
			if (this.cartaArribaMazoAbajo(cartaAuxiliar)){
				this.jugadores.get(this.jugadorActual).tirarCarta(indice - 1);
				this.mazoAbajo.agregarCarta(cartaAuxiliar);
				opcion = '3';
			if (cartaAuxiliar.getNumero() == 12) {
				if (this.getSentidoJuego() == 1) {
					this.setSentidoJuego(-1);
					}
				else {this.setSentidoJuego(1);}
				opcionespecial = "12";

				}
			else if (cartaAuxiliar.getNumero() == 11) {
				cartaAuxiliar = null;
				opcionespecial = "11";
				
				}
			else if (cartaAuxiliar.getNumero() == 10) {
				opcionespecial = "10";
				}
			else if (cartaAuxiliar.getNumero() == 7) {
				cartaAuxiliar = null;
				opcionespecial = "7";
				}
			else if (cartaAuxiliar.getNumero() == 4) {
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
			
			else if (opcionespecial == "4") {this.notificar(Eventos.CARTA_ESPECIAL_4);this.cambiojugadorActual(false);this.cambiojugadorActual(true);}
			
			else if (opcionespecial == "7") {this.notificar(Eventos.CARTA_ESPECIAL_7);}
			
			else if (opcionespecial == "10") {this.notificar(Eventos.CARTA_ESPECIAL_10);}
			
			else if (opcionespecial == "11") {this.notificar(Eventos.CARTA_ESPECIAL_11);}
			
			else if (opcionespecial == "12") {this.notificar(Eventos.CARTA_ESPECIAL_12);}
			
		}		
		
		return cartaAuxiliar;
	}
	
	public boolean terminaRonda() {
		if (!this.jugadores.get(this.jugadorActual).getJodete() && (this.jugadores.get(this.jugadorActual).jodete())) {
			this.cantarJodete(); 
			}
		else {
			if (this.jugadores.get(this.jugadorActual).cantidadCartasCero()) {
				return true;
			}
		}
		return false;
	}
	
	public void cambioJugadorInicial() {
		int jugadorIni;
		jugadorIni = this.jugadorInicial + 1;
		if (jugadorIni < this.numeroJugadores) {setJugadorInicial(jugadorIni);}
		else {setJugadorInicial(0); jugadorIni = 0;}
		this.mazoArriba.limpiarMazo();
		this.mazoAbajo.limpiarMazo();
		for (Jugador jugador : this.jugadores) {
			jugador.cantidadCartasCero();
		}
		this.generarMazoArriba();
		this.notificar(Eventos.CAMBIAR_RONDA);
		
	}
	
	public void cambiojugadorActual(boolean cambio) {
		int jugadorAct;
		jugadorAct = this.jugadorActual + this.getSentidoJuego();
		this.jugadores.get(this.jugadorActual).setJodete(false);
		if (jugadorAct == -1) {setJugadorActual(this.numeroJugadores - 1);}
		else if (jugadorAct < this.numeroJugadores) {setJugadorActual(jugadorAct);}
		else {setJugadorActual(0);}
		if (cambio) {this.notificar(Eventos.CAMBIAR_JUGADOR);}

		
	}

	public int getSentidoJuego() {
		return sentidoJuego;
	}

	public void setSentidoJuego(int sentidoJuego) {
		this.sentidoJuego = sentidoJuego;
	}

	public void cartaComodin10(String p) {
		this.setPaloCarta10(this.mazoAbajo.obtenerUltimaCarta().cambiarUnPalo(p)); 
		this.notificar(Eventos.CAMBIO_COLOR);
		
	}

	public String mostrarManoJugador() {
		String cadena = "";
		int i = 1;
		for (Carta carta : this.jugadores.get(jugadorActual).getMano()) {
			
			cadena =cadena + (i++) +" - " + carta.toString() + "\n";
					
		}
		return cadena;
	}
	
	public String mostrarCartaMazoAbajo() {
		String carta = "";
		if(!(this.mazoAbajo.tamanioIgualCero())) {
			carta = "Carta boca arriba: " + this.mazoAbajo.obtenerUltimaCarta().toString();}
		return (carta);
	}
	
	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	private void generarMazoArriba() {
		mazoArriba.generarMazo();
		mazoArriba.mezclar();
	};
	
	public void setJugadores(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public boolean  robarCarta() {
		boolean resultado = true;
	
		if(!this.mazoArriba.tamanioIgualCero()) {
			if (!this.mazoAbajo.tamanioIgualCero()) {
				this.mazoArriba.pasarCartasDeUnMazo(mazoAbajo);
			}
		}	
		
		else {resultado = false;
				this.notificar(Eventos.NO_ES_POSIBLE_ROBAR_CARTAS);}	
	
		if (resultado = true) {
			this.jugadores.get(this.jugadorActual).sumarCarta(this.mazoArriba.getMazo().get(0));
			this.mazoArriba.eliminarCarta(0);	
		}
		
		return resultado;
		
	}
	
	public void cargarNombreJugadores(String nombre, int i) {

			this.jugadores.get(i).setNombre(nombre);
			
			if ((i+1) == this.numeroJugadores) {
				this.notificar(Eventos.COMIENZA_EL_JUEGO);
			}
		
	}
	
	public void cantarJodete() {
		for (int i = 0; i < 5; i++) {
			if (!this.robarCarta()) {
				break;													
			}	
		}
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
		for (Observador observador : this.observadores) {
			observador.actualizar(evento, this);
		}
	}


	public int getNumeroJugadores() {
		return numeroJugadores;
	}



	public void setNumeroJugadores(int numeroJugadores) {
		this.numeroJugadores = numeroJugadores;
	}

	private void setPaloCarta10(Palo palo) {
		this.paloCarta10 = palo;
	}
}
