package ar.edu.unlu.juego;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import ar.edu.unlu.baraja.*;
import ar.edu.unlu.jugador.*;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;


public class Juego extends ObservableRemoto implements IJuego,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8947140650500952205L;

	private static final Integer numeroMaximoJugadores = 6;
	
	private static final Integer numeroMinimoJugadores = 2;

	private Integer jugadorInicial;
	
	private Integer jugadorActual;
	
	private Integer numeroJugadores;
	
	private ArrayList<Jugador> jugadores;
		
	private Mazo mazoArriba;
	
	private Mazo mazoAbajo;
	
	private Integer sentidoJuego = 1;
	
	
	public Juego() {
		super();
		setJugadorInicial(0);
		setJugadorActual(getJugadorInicial());
		jugadores = new ArrayList<>();	
		mazoArriba = new Mazo();
		mazoAbajo = new Mazo();
		this.mazoArriba.cargarMazo();
		
	}

	private ArrayList<Mazo> repartirCartaJugador() {  //reparte las cartas del mazo
		ArrayList<Mazo> mazoJugadores = new ArrayList<>();
		Integer cont = 0;

		for (Integer i = 0; i < this.getNumeroJugadores(); i++) {
			mazoJugadores.add(new Mazo());
		}
		cont = 0;

		for (Integer i = 0; i < (this.getNumeroJugadores()*ReglasNumeroJugadores.cartasARepartir(this.getNumeroJugadores())); i++) {
			mazoJugadores.get(cont).agregarCarta(mazoArriba.getMazo().get(i));
			if ((cont + 1) == this.getNumeroJugadores() ) {
				cont = 0;			
			}
			else {cont++;}
		}
		
		for(Integer i = 0; i < this.getNumeroJugadores()*ReglasNumeroJugadores.cartasARepartir(this.getNumeroJugadores()); i++) {
			this.mazoArriba.eliminarCarta(0);
		}
		
		return mazoJugadores;
	}

	private void cargarJugadores() { //crea los jugadores y reparte las cartas
		ArrayList<Mazo> mazoJugadores = repartirCartaJugador();
		this.crearJugadores();
		for (Integer i = 0; i < this.getNumeroJugadores(); i++) {
			jugadores.get(i).setMano(mazoJugadores.get(i).getMazo());
		}		
	}
	private void crearJugadores() {// crea los jugadores
		for (Integer i = 0; i < this.getNumeroJugadores(); i++) {
			jugadores.add(new Jugador());
		}		
	}
	
	@Override
	public boolean cantidadJugadores(Integer cant) throws RemoteException {// verifica si la cantidad de jugadores es correcta
		boolean veri = true;
		if ((cant >= numeroMinimoJugadores) && (cant <= numeroMaximoJugadores)) {
			this.setNumeroJugadores(cant);
			cargarJugadores();
			this.notificarObservadores(Eventos.CANTIDAD_JUGADORES);
		}
		else {
			veri = false;
			this.notificarObservadores(Eventos.CANTIDAD_JUGADORES_ERRONEA);
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
	
	@Override
	public ICarta tirarCarta(Integer indice) throws RemoteException { // tira la carta seleccionada
		Carta cartaAuxiliar = null;
		
		if (! this.getJugadorRequerido().esCartaValida(indice)) { // verifica si el indice de la carta se encuatra en el rango del tamano de la mano del jugador
			this.notificarObservadores(Eventos.CARTA_INEXISTENTE);
		}
		else {
			cartaAuxiliar = obtenerCartaIndice(indice);
			if(! this.cartaArribaMazoAbajo(cartaAuxiliar)) {
				cartaAuxiliar = null;
				this.notificarObservadores(Eventos.CARTA_NO_COINCIDENTE);
			}
			else {
				this.getJugadorRequerido().tirarCarta(indice - 1);
				this.mazoAbajo.agregarCarta(cartaAuxiliar);
				CartaFuncion.funcionamientoCartas(cartaAuxiliar.getNumero(), this, cartaAuxiliar);				
			}
		}		
		
		this.notificarObservadores(Eventos.CARTAS);
		
		return cartaAuxiliar;
	}
	
	@Override
	public boolean terminaRonda() throws RemoteException { //termina la ronda
		boolean resultado = false;
		if (!(this.getJugadorRequerido().getJodete()) && (this.getJugadorRequerido().jodete())) {
			this.cantarJodete(); 
			}
		
		if (this.getJugadorRequerido().cantidadCartasCero()) {
			resultado = true;			
		}
		return resultado;
	}
	
	@Override
	public void cambioJugadorInicial() throws RemoteException {// cambia el jugador que comienza
		Integer jugadorIni;
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
		this.notificarObservadores(Eventos.CAMBIAR_RONDA);	
		this.notificarObservadores(Eventos.CAMBIAR_JUGADOR);
		this.notificarObservadores(Eventos.CARTAS);
		
	}
	
	@Override
	public void cambiojugadorActual(boolean cambio) throws RemoteException { // cambia al jugador que sigue
		Integer jugadorAct;
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
		if (cambio) {this.notificarObservadores(Eventos.CAMBIAR_JUGADOR);this.notificarObservadores(Eventos.CARTAS);}		
	}

	@Override
	public void cartaComodin10(String p) throws RemoteException { // logica de la carta numero 10
		this.mazoAbajo.obtenerUltimaCarta().setcambioPalo(this.mazoAbajo.obtenerUltimaCarta().cambiarUnPalo(p)); //modificar???????????
		this.notificarObservadores(Eventos.CAMBIO_COLOR);		
	}

	@Override
	public String mostrarManoJugador() {// muestra las cartas del jugador
		String cadena = "";
		Integer i = 1;
		
		for (Carta carta : this.jugadores.get(getJugadorActual()).getMano()) {
			if (carta.getNumero() == 0) {
				cadena = cadena + (i++) + "-" + "   COMODIN" + "\n" ;
			}
			else {cadena =cadena + (i++) +" - " + carta.toString() + "\n";}
		}
		return cadena;
	}
	
	@Override
	public String mostrarCartaMazoAbajo() {
		String carta = "";
		if(!(this.mazoAbajo.tamanioIgualCero())) {
			carta = "Carta boca arriba: " + this.mazoAbajo.obtenerUltimaCarta().toString();}
		return (carta);
	}
	
	@Override
	public void pasarJugador() throws RemoteException {
	if (this.terminaRonda()) {
		this.cambioJugadorInicial();
	}
	else{this.cambiojugadorActual(true);}		
	}
	
	
	@Override
	public boolean  robarCarta(boolean mostrar) throws RemoteException {
		boolean resultado = true;
	
		if(this.mazoArriba.tamanioIgualCero()) {
			if (!this.mazoAbajo.tamanioIgualCero()) {
				this.mazoArriba.pasarCartasDeUnMazo(mazoAbajo);
				
			}
			else {
				resultado = false;
				this.notificarObservadores(Eventos.NO_ES_POSIBLE_ROBAR_CARTAS);
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
		
		if (mostrar) {this.notificarObservadores(Eventos.CARTAS);}
		return resultado;		
	}
	
	@Override
	public void cargarNombreJugadores(String nombre, Integer i) throws RemoteException {
			this.jugadores.get(i).setNombre(nombre);			
			if ((i+1) == this.numeroJugadores) {
				this.notificarObservadores(Eventos.COMIENZA_EL_JUEGO);
				this.notificarObservadores(Eventos.JUGADOR_INICIAL);
				this.notificarObservadores(Eventos.CARTAS);
			}	
	}
	
	@Override
	public void cantarJodete() throws RemoteException {  //MODIFICAR?????????
		for (Integer i = 0; i < 5; i++) {
			if (!this.robarCarta(false)) {
				break;													
			}	
		}
		this.notificarObservadores(Eventos.CARTAS);
	}
	
	@Override
	public void cantoJodete() throws RemoteException {
		if (this.jugadores.get(this.jugadorActual).jodete()) {
			this.jugadores.get(this.jugadorActual).setJodete(true);			
		}
		else {cantarJodete();}		
	}

	@Override
	public String getNombreJugadorActual() {
		return this.jugadores.get(jugadorActual).getNombre();
	}

	@Override
	public Mazo getMazoAbajo() {
		return mazoAbajo;
	}
	
	private Carta obtenerCartaIndice(Integer indice) {
		return this.getJugadorRequerido().obtenerCarta(indice - 1);
	}
		
	@Override
	public Integer getJugadorInicial() {
		return jugadorInicial;
	}

	@Override
	public void setJugadorInicial(Integer jugadorInicial) {
		this.jugadorInicial = jugadorInicial;
	}

	public Integer getJugadorActual() {
		return jugadorActual;
	}
	
	private Jugador getJugadorRequerido() {
		return this.jugadores.get(this.getJugadorActual());
	};
	
	private void setJugadorActual(Integer jugadorActual) {
		this.jugadorActual = jugadorActual;
	}
	
	private void setNumeroJugadores(Integer cant) {
		this.numeroJugadores = cant;		
	}


	@Override
	public Integer getSentidoJuego() {
		return sentidoJuego;
	}

	@Override
	public void setSentidoJuego(Integer sentidoJuego) {
		this.sentidoJuego = sentidoJuego;
	}
	
	@Override
	public Integer getNumeroJugadores() {
		return numeroJugadores;
	}
	
	@Override
	public Integer getCartaEspecial2() {
		Carta carta = this.mazoAbajo.obtenerUltimaCarta();
		if(carta == null) {
			return 0;
		}
		else{
			return carta.getLevantar2Cartas();
		}	
	}

}
