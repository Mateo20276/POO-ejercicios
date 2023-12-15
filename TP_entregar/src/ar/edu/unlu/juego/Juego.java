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

	private ArrayList<Mazo> repartirCartaJugador() {  //reparte las cartas del mazo
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

	private void cargarJugadores() { //crea los jugadores y reparte las cartas
		ArrayList<Mazo> mazoJugadores = repartirCartaJugador();
		this.crearJugadores();
		for (int i = 0; i < this.getNumeroJugadores(); i++) {
			jugadores.get(i).setMano(mazoJugadores.get(i).getMazo());
		}		
	}
	private void crearJugadores() {// crea los jugadores
		for (int i = 0; i < this.getNumeroJugadores(); i++) {
			jugadores.add(new Jugador());
		}		
	}
	
	public boolean cantidadJugadores(int cant) {// verifica si la cantidad de jugadores es correcta
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
	


	private boolean cartaArribaMazoAbajo(Carta carta) {//verifica la carta boca arriba del mazo antes de tirar una carta
		boolean resultado = false;	

		if(this.mazoAbajo.tamanioIgualCero()) {
			resultado = this.mazoAbajo.tamanioIgualCero();
		}
		
		else if ((this.mazoAbajo.obtenerUltimaCarta().getNumero() == carta.getNumero()) || (this.mazoAbajo.obtenerUltimaCarta().getPalo() == carta.getPalo())) {
			if (!(carta.getcambioPalo() == null)){// verifica la carta especial 10
				carta.setPalo(carta.getcambioPalo());carta.setcambioPalo(null);
				}
			resultado = true;
			if (!(this.mazoAbajo.obtenerUltimaCarta().getNumero() == carta.getNumero())&&(this.getCartaEspecial2()) != 0) { //verifica carta especial 2
				resultado = false;
			}			
			else if (this.mazoAbajo.obtenerUltimaCarta().getNumero() == carta.getNumero()&&(this.getCartaEspecial2()) != 0){
				this.mazoAbajo.obtenerUltimaCarta().sumLevantar2Cartas();				
			}
		}
				
		return resultado;		
	}
	
	public ICarta tirarCarta(int indice) { // tira la carta seleccionada
		Carta cartaAuxiliar = null;
		
		if (! this.getJugadorRequerido().esCartaValida(indice)) { // verifica si el indice de la carta se encuatra en el rango del tamano de la mano del jugador
			this.notificar(Eventos.CARTA_INEXISTENTE);
		}
		else {
			cartaAuxiliar = obtenerCartaIndice(indice);
			if(! this.cartaArribaMazoAbajo(cartaAuxiliar)) {
				cartaAuxiliar = null;
				this.notificar(Eventos.CARTA_NO_COINCIDENTE);
			}
			else {
				this.getJugadorRequerido().tirarCarta(indice - 1);
				this.mazoAbajo.agregarCarta(cartaAuxiliar);
				CartaFuncion.funcionamientoCartas(cartaAuxiliar.getNumero(), this, cartaAuxiliar);				
			}
		}		
		
		this.notificar(Eventos.CARTAS);
		
		return cartaAuxiliar;
	}
	
	public boolean terminaRonda() { //termina la ronda
		boolean resultado = false;
		if (!(this.getJugadorRequerido().getJodete()) && (this.getJugadorRequerido().jodete())) {
			this.cantarJodete(); 
			}
		
		if (this.getJugadorRequerido().cantidadCartasCero()) {
			resultado = true;			
		}
		return resultado;
	}
	
	public void cambioJugadorInicial() {// cambia el jugador que comienza
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
	
	public void cambiojugadorActual(boolean cambio) { // cambia al jugador que sigue
		int jugadorAct;
		jugadorAct = this.getJugadorActual() + this.getSentidoJuego();
		this.getJugadorRequerido().setJodete(false);
		if (jugadorAct == -1) {setJugadorActual(this.getNumeroJugadores() - 1);}
		else if (jugadorAct < this.getNumeroJugadores()) {setJugadorActual(jugadorAct);}
		else {setJugadorActual(0);}
		if (!this.mazoAbajo.tamanioIgualCero()) {
			if (this.mazoAbajo.obtenerUltimaCarta().getCambioJugador()) {
				this.mazoAbajo.obtenerUltimaCarta().setCambioJugador(false);
				this.cambiojugadorActual(false);
			}
		}
		if (cambio) {this.notificar(Eventos.CAMBIAR_JUGADOR);this.notificar(Eventos.CARTAS);}		
	}

	public void cartaComodin10(String p) { // logica de la carta numero 10
		this.mazoAbajo.obtenerUltimaCarta().setcambioPalo(this.mazoAbajo.obtenerUltimaCarta().cambiarUnPalo(p)); //modificar???????????
		this.notificar(Eventos.CAMBIO_COLOR);		
	}

	public String mostrarManoJugador() {// muestra las cartas del jugador
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
			else {
				resultado = false;
				this.notificar(Eventos.NO_ES_POSIBLE_ROBAR_CARTAS);
			}	
		}			
		
		if (resultado){						
			this.getJugadorRequerido().sumarCarta(this.mazoArriba.getMazo().get(0));
			this.mazoArriba.eliminarCarta(0);	

			if((!this.mazoAbajo.tamanioIgualCero())&& !(this.getCartaEspecial2() == 0)) {
				this.mazoAbajo.obtenerUltimaCarta().restLevantar2Cartas();
				this.robarCarta(false);
			}
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
	
	public void cantarJodete() {  //MODIFICAR?????????
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
	
	private Carta obtenerCartaIndice(int indice) {
		return this.getJugadorRequerido().obtenerCarta(indice - 1);
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
	
	private Jugador getJugadorRequerido() {
		return this.jugadores.get(this.getJugadorActual());
	};
	
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

	public void setSentidoJuego(int sentidoJuego) {
		this.sentidoJuego = sentidoJuego;
	}
	
	public int getNumeroJugadores() {
		return numeroJugadores;
	}
	
	public int getCartaEspecial2() {
		Carta carta = this.mazoAbajo.obtenerUltimaCarta();
		if(carta == null) {
			return 0;
		}
		else{
			return carta.getLevantar2Cartas();
		}

	}

}
