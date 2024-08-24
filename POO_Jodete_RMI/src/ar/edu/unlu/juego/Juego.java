package ar.edu.unlu.juego;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import ar.edu.unlu.baraja.*;
import ar.edu.unlu.jugador.*;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;
import ar.edu.unlu.vista.EstadoJuego;


public class Juego extends ObservableRemoto implements IJuego,Serializable{
	private static final long serialVersionUID = 1L;
	private static final Integer numeroMaximoJugadores = 6;	
	private static final Integer numeroMinimoJugadores = 2;
	private static final Integer puntosPerdedor = 5;
	private Integer jugadorInicial;	
	private Integer jugadorActual;	
	private Integer numeroJugadores = 0;	
	private ArrayList<Jugador> jugadores;		
	private Mazo mazoArriba;	
	private Mazo mazoAbajo;	
	private Palo palo;
	private Integer sentidoJuego = 1;
	private boolean opcionb = true;
	private boolean opcionc = true;
	private boolean opciond = false;
	private boolean opcionf = false;	
	private EstadoJuego estadoJuegoActualopciones =	EstadoJuego.OPCION_SELECCIONADA;
	private String op;
	private ICarta cartaEnJuego;
	private String algo;
	private Integer ultimoJugador;
	
	public Juego() {
		super();
		setJugadorInicial(0);
		setJugadorActual(getJugadorInicial());
		jugadores = new ArrayList<>();	
		mazoArriba = new Mazo();
		mazoAbajo = new Mazo();
		this.mazoArriba.cargarMazo();
		cartaEnJuego = new Carta(99, null);
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
		for (Integer i = 0; i < this.getNumeroJugadores(); i++) {
			jugadores.get(i).setMano(mazoJugadores.get(i).getMazo());
		}
	}
	
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

	private boolean cartaArribaMazoAbajo(Carta carta) throws RemoteException {//verifica la carta boca arriba del mazo antes de tirar una carta
		boolean resultado = false;	

		if(this.mazoAbajo.tamanioIgualCero()) {
			resultado = this.mazoAbajo.tamanioIgualCero();
		}
		
		else if ((ultimaCartaMazo().getNumero() == carta.getNumero()) || (getPalo() == carta.getPalo()) || (carta.getNumero() == 0) || (ultimaCartaMazo().getNumero() == 0)) {
			resultado = true;
			//test("palo y numero en juego: " + getPalo() + " y " + ultimaCartaMazo().getNumero());
			//test("palo y numero tirado: " + carta.getPalo() + " y " + carta.getNumero());;
			if (!(ultimaCartaMazo().getNumero() == carta.getNumero())&&(this.getCartaEspecial2()) != 0) { //verifica carta especial 2
				resultado = false;
			}			
			else if (ultimaCartaMazo().getNumero() == carta.getNumero()&&(this.getCartaEspecial2()) != 0){
				this.mazoAbajo.obtenerUltimaCarta().sumLevantar2Cartas();				
			}
		}
		return resultado;		
	}
	private Carta ultimaCartaMazo() {
		return this.mazoAbajo.obtenerUltimaCarta();
	}
	
	public ICarta tirarCarta(Integer indice) throws RemoteException { // tira la carta seleccionada
		Carta cartaAuxiliar = null;
		if (!this.getJugadorRequerido().esCartaValida(indice)) { // verifica si el indice de la carta se encuatra en el rango del tamano de la mano del jugador
			this.notificarObservadores(Eventos.CARTA_INEXISTENTE);
		}
		else {
			cartaAuxiliar = obtenerCartaIndice(indice);
			if(!this.cartaArribaMazoAbajo(cartaAuxiliar)) {
				cartaAuxiliar = null;
			}
			else {
		    	this.setPalo(cartaAuxiliar.getPalo());
				this.getJugadorRequerido().tirarCarta(indice - 1);
				this.mazoAbajo.agregarCarta(cartaAuxiliar);
				CartaFuncion.funcionamientoCartas(cartaAuxiliar.getNumero(), this, cartaAuxiliar);				
			}
		}		
		
		this.notificarObservadores(Eventos.CARTAS);		
		return cartaAuxiliar;
	}

	public EstadoJuego opcionesDeJuego(EstadoJuego estado, String cartanum) throws NumberFormatException, RemoteException {
		switch((EstadoJuego) estado) {//todas las posible opciones del juego
		
		case OPCION_B: opciond = true; opcionb = false; opcionc = false;
				       ICarta carta = this.opcionb(cartanum);			       
				       if (estadoJuegoActualopciones == EstadoJuego.TIRANDO_CARTA) {
				    	   if(carta == null) {opcionb = true; opcionc = true; opciond = false;  
							this.notificarObservadores(Eventos.CARTA_NO_COINCIDENTE);
					    	estadoJuegoActualopciones = EstadoJuego.OPCION_SELECCIONADA;	
						    return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
					       }
				    	   this.cartaEnJuego = carta;
				    	   this.notificarObservadores(Eventos.CARTA_TIRADA);
				    	   estadoJuegoActualopciones = EstadoJuego.OPCION_SELECCIONADA;	
					       if (carta.getNumero() == 7 || carta.getNumero()== 11) {
								opcionb = true; opcionc = false; opciond = true;
							}
					       if (carta.getNumero() == 10) {
								return EstadoJuego.CAMBIO_PALO;
							}
					       return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
				       }
				       
				       else {
							estadoJuegoActualopciones = EstadoJuego.TIRANDO_CARTA;			    	   
				       }
				       return EstadoJuego.TIRANDO_CARTA;
		
		case OPCION_C:	opcionc = false;
						opciond = true;

						if (this.cartaEnJuego.getNumero() == 0 && this.cartaEnJuego.getJodete0()) { //algo no funciona con el 0
							levantarPorJodete(this.getJugadorRequerido());
						}						
						else if(!this.robarCarta(true, getJugadorRequerido())) {
							opcionc = true;
							opciond = false;
						}
						return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;			
		
		case OPCION_D: 	this.pasarJugador();
						opciond = false;
						opcionc = true;
						opcionb = true;	
						if (this.cartaEnJuego.getNumero() == 0 && this.cartaEnJuego.getJodete0()) {
							opcionb = false;
						}
						cartaEnJuego = new Carta(99, null);
						return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
			
		case OPCION_E:  this.cantoJodete();
						return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
						
		case OPCION_G:	this.verificarJodeteAnterior();
						return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
						
		case OPCION_INVALIDA:return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;			
						}
		return estado;
		}

	
	public boolean terminaRonda() throws RemoteException { //termina la ronda
		boolean resultado = false;
		if (this.getJugadorRequerido().cantidadCartasCero()) {
			resultado = true;			
		}
		return resultado;
	}
	
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
	
	public void cambiojugadorActual(boolean cambio) throws RemoteException { // cambia al jugador que sigue
		Integer jugadorAct = this.getJugadorActual() + this.getSentidoJuego();
		this.setUltimoJugador(this.getJugadorActual());
		if (jugadorAct == -1) {setJugadorActual(this.getNumeroJugadores() - 1);}
		else if (jugadorAct < this.getNumeroJugadores()) {setJugadorActual(jugadorAct);}
		else {setJugadorActual(0);}	
		this.jugadores.get(this.jugadorActual).setJodete(false);
		if (!this.mazoAbajo.tamanioIgualCero()) {
			if (this.mazoAbajo.obtenerUltimaCarta().getCambioJugador()) {
				this.mazoAbajo.obtenerUltimaCarta().setCambioJugador(false);
				this.cambiojugadorActual(false);
			}
		}
		if (cambio) {this.notificarObservadores(Eventos.CAMBIAR_JUGADOR);this.notificarObservadores(Eventos.CARTAS);}		
	}

	public void cartaComodin10(String p) throws RemoteException { // logica de la carta numero 10
		this.setPaloStr(p); 
		this.notificarObservadores(Eventos.CAMBIO_COLOR);		
	}

	public String mostrarManoJugador(String jugador) {// muestra las cartas del jugador
		String cadena = "";
		Integer i = 1;
		
		Integer indice = obtenerjugadorNombre(jugador);
		
		for (Carta carta : this.jugadores.get(indice).getMano()) {
			if (carta.getNumero() == 0) {
				cadena = cadena + (i++) + "-" + "   COMODIN" + "\n" ;
			}
			else {cadena =cadena + (i++) +" - " + carta.toString() + "\n";}
		}
		return cadena;
	}

	public String mostrarCartaMazoAbajo() {
		String carta = "";
		if(!this.mazoAbajo.tamanioIgualCero()) {
			carta = this.mazoAbajo.obtenerUltimaCarta().toString();}
		return (carta);
	}

	public void pasarJugador() throws RemoteException {
	if (this.terminaRonda()) {
		//algo no anda
		this.calcularPuntosJugadores();
		this.eleminarJugagores();
		this.terminarJuego();
		this.cambioJugadorInicial();
		primeraCarta();
	}
	else{
		this.cambiojugadorActual(true);}		
	}

	public boolean  robarCarta(boolean mostrar, Jugador jugador) throws RemoteException {
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
			
			this.mazoAbajo.obtenerUltimaCarta().setJodete0(false);
			jugador.sumarCarta(this.mazoArriba.getMazo().get(0));
			this.mazoArriba.eliminarCarta(0);	
			if(!(this.getCartaEspecial2() == 0)) {
				opcionb=false;
				this.mazoAbajo.obtenerUltimaCarta().restLevantar2Cartas();
				this.robarCarta(false, jugador);
			}
		}
		
		if (mostrar) {this.notificarObservadores(Eventos.CARTAS);}
		return resultado;		
	}

	public void cargarNombreJugadores(String nombre) throws RemoteException {
			this.jugadores.add(new Jugador());
			this.jugadores.get(this.getNumeroJugadores()).setNombre(nombre);	
			this.setNumeroJugadores(this.getNumeroJugadores() + 1);
			if(this.getNumeroJugadores() == 1 ) {
				this.notificarEsperandoJugadores();
			}
			else if(this.getNumeroJugadores() >= 2 ) {
				this.notificarObservadores(Eventos.LISTOS_PARA_COMENZAR);
			}
	}

	private ICarta opcionb(String cartanum) throws NumberFormatException, RemoteException {	
		if (!(cartanum.equals("")) && (!cartanum.equals("b"))) {
			ICarta cartaax = this.tirarCarta(Integer.parseInt(cartanum));
			estadoJuegoActualopciones = EstadoJuego.TIRANDO_CARTA;
			return cartaax;
		}
		else {
			this.notificarObservadores(Eventos.SELECCIONAR_CARTA_A_TIRAR);
			return null;}
	}
		
	public void comenzarJuego() throws RemoteException{
		cargarJugadores();
		this.primeraCarta();
		this.notificarObservadores(Eventos.COMIENZA_EL_JUEGO);
		this.notificarObservadores(Eventos.JUGADOR_INICIAL);
		this.notificarObservadores(Eventos.CARTAS);
	}

	public void levantarPorJodete(Jugador jugador) throws RemoteException { 
		for (Integer i = 0; i < 5; i++) {
			if (!this.robarCarta(false, jugador)) {
				break;													
			}	
		}
		this.notificarObservadores(Eventos.CARTAS);
	}

	public void cantoJodete() throws RemoteException {
		getJugadorRequerido().setJodete(true);	
		this.notificarObservadores(Eventos.CANTO_JODETE);
		if (!getJugadorRequerido().jodete()) {	
			if (this.cartaEnJuego.getNumero() == 0) {
				this.cartaEnJuego.setJodete0(true);
				getJugadorRequerido().setJodete(false);	
			}
			else {
				levantarPorJodete(getJugadorRequerido());
				this.notificarObservadores(Eventos.JODETE_LEVENTAS_MAL_CANTADO);
			}
		}	
		if (this.cartaEnJuego.getNumero() == 0) {
			this.cartaEnJuego.setJodete0(true);
			this.notificarObservadores(Eventos.JODETE_LEVANTAS_0_CANTADO);
		}
	}
	
	private void verificarJodeteAnterior() throws RemoteException {
		Jugador ultimoJugador = this.jugadores.get(this.getUltimoJugador());
		if (!(ultimoJugador.getJodete()) && (ultimoJugador.jodete())) {
			levantarPorJodete(ultimoJugador);
			this.notificarObservadores(Eventos.JODETE_LEVENTAS_NO_CANTASTE);
		}
		else {levantarPorJodete(getJugadorRequerido());	
			this.notificarObservadores(Eventos.JODETE_LEVENTAS_MAL_CANTADO);
		}
	}

	public String getNombreJugadorActual() {
		return getJugadorRequerido().getNombre();
	}

	public Integer obtenerjugadorNombre(String nombre) {
		for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombre().equals(nombre)) {
                return i; // Retorna la posición del jugador en el array
            }
        }
        return -1;// No se encontró ningún jugador con el nombre dado
	}
	
	public void jugadorAJugar() throws RemoteException {
		this.notificarObservadores(Eventos.MOSTRAR_OPCIONES);
	}	
	public void seleccionarOpcion(String opcion) throws RemoteException {
		this.op = opcion;
		this.notificarObservadores(Eventos.SELECCIONAR_OPCIONES);
	}	
	public void notificarEsperandoJugadores() throws RemoteException {
		this.notificarObservadores(Eventos.ESPERANDO_JUGADORES);
	}
	public Mazo getMazoAbajo() {
		return mazoAbajo;
	}	
	private Carta obtenerCartaIndice(Integer indice) {
		return this.getJugadorRequerido().obtenerCarta(indice - 1);
	}
	public Integer getJugadorInicial() {
		return jugadorInicial;
	}
	public void setJugadorInicial(Integer jugadorInicial) {
		this.jugadorInicial = jugadorInicial;
	}
	public Integer getJugadorActual() {
		return jugadorActual;
	}	
	private Jugador getJugadorRequerido() {
		return this.jugadores.get(this.getJugadorActual());
	}	
	private void setJugadorActual(Integer jugadorActual) {
		this.jugadorActual = jugadorActual;
	}	
	private void setNumeroJugadores(Integer cant) {
		this.numeroJugadores = cant;		
	}
	public Integer getSentidoJuego() {
		return sentidoJuego;
	}
	public void setSentidoJuego(Integer sentidoJuego) {
		this.sentidoJuego = sentidoJuego;
	}
	public Integer getNumeroJugadores() {
		return numeroJugadores;
	}
	public boolean getOpcionb() {
		return this.opcionb;
	}	
	public boolean getOpcionc() {
		return this.opcionc;
	}
	public boolean getOpciond() {
		return this.opciond;
	}
	public boolean getOpcionf() {
		return this.opcionf;
	}
	public String getUltimoJugadorAgregado() throws RemoteException {
		return this.jugadores.get(jugadores.size()-1).getNombre();
	}
	public Integer getCartaEspecial2() {
		Carta carta = this.mazoAbajo.obtenerUltimaCarta();
		if(carta == null) {
			return 0;
		}
		else{
			return carta.getLevantar2Cartas();
		}	
	}
	public String getOp()throws RemoteException  {
		return this.op;
	}	
	public void setOp(String string)throws RemoteException  {
		this.op = string;
	}
	public Integer getCartaEnJuegoNumero() throws RemoteException {
		return this.cartaEnJuego.getNumero();
	}	
	public String getCartaEnJuegoPalo() throws RemoteException {
		return this.cartaEnJuego.getPaloStr();
	}
	public void test(String algo) throws RemoteException {
		this.algo = algo;
		this.notificarObservadores(Eventos.TEST);
	}
	public String getalgo()throws RemoteException  {
		return this.algo;
	}
	public void setUltimoJugador(Integer jugador) {
		this.ultimoJugador = jugador;
	}
	public Integer getUltimoJugador() {
		return this.ultimoJugador;
	}
	public void setPaloStr(String palo) {
		switch (palo.toLowerCase()) {
		case "copa":
			setPalo(Palo.COPA);
			break;			
		case "oro":
			setPalo(Palo.ORO);
			break;			
		case "espada":
			setPalo(Palo.ESPADA);
			break;			
		case "basto":
			setPalo(Palo.BASTO);
			break;
		}
	}
	private void setPalo(Palo palo) {
		this.palo = palo;
	}
	public Palo getPalo() {
		return this.palo;
	}
	private void primeraCarta() throws RemoteException {
		Carta carta = this.mazoArriba.getMazo().get(0);
		this.mazoAbajo.agregarCarta(carta);
		this.mazoArriba.eliminarCarta(0);
		CartaFuncion.funcionamientoCartas(carta.getNumero(), this, carta); //modificar
		this.setPalo(ultimaCartaMazo().getPalo());
	}
	
	private void calcularPuntosJugadores() {
		for (Integer i = 0; i < this.getNumeroJugadores(); i++) {
			Integer x = 1;
			if (this.cartaEnJuego.getNumero() == 0 || this.cartaEnJuego.getNumero() == 10) {
				x = 2;
			}
			Jugador jugador = this.jugadores.get(i);
			if (i != this.getJugadorActual()) {
				jugador.SumarPuntos(x);
			}	
			if (jugador.getPuntos() >= puntosPerdedor){
				jugador.setPerdio(true);
			}
		}
	}
	private void eleminarJugagores() throws RemoteException {
		for (Integer i = 0; i < this.getNumeroJugadores(); i++) {
			Jugador jugador = this.jugadores.get(i);
			if (jugador.getPerdio()) {
				this.jugadores.remove(i);
				this.setNumeroJugadores(this.getNumeroJugadores() - 1);
				i = 99;
			}
		}
	}
	private void terminarJuego() throws RemoteException {
		if (this.getNumeroJugadores() == 1) {
			this.notificarObservadores(Eventos.JUEGO_TERMINADO);
		}
		
	}
}
