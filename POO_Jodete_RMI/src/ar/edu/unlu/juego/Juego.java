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
	private static final Integer puntosPerdedor = 10;
	private Integer jugadorInicial;	
	private Integer jugadorActual;	
	private ArrayList<Jugador> jugadores;		
	private Mazo mazoArriba;	
	private Mazo mazoAbajo;	
	private Palo palo;
	private Integer cartasALevantar = 0;
	private Integer sentidoJuego = 1;
	private boolean opcionb = true;
	private boolean opcionc = true;
	private boolean opciond = false;
	private boolean opcionf = false;	
	private EstadoJuego estadoJuegoActualopciones =	EstadoJuego.OPCION_SELECCIONADA;
	private String op;
	private ICarta cartaEnJuego;
	private String algo;
	private Integer ultimoJugador = -1;
	private String ultimoJugadorEliminado;
	
	public Juego() {
		super();
		setJugadorInicial(0);
		setJugadorActual(getJugadorInicial());
		jugadores = new ArrayList<Jugador>();	
		mazoArriba = new Mazo();
		mazoAbajo = new Mazo();
		this.mazoArriba.cargarMazo();
		cartaEnJuego = new Carta(99, Palo.NULL);
	}

	private ArrayList<Mazo> repartirCartaJugador() {  //reparte las cartas del mazo
		ArrayList<Mazo> mazoJugadores = new ArrayList<>();
		Integer cont = 0;
		for (Integer i = 0; i < this.jugadores.size(); i++) {
			mazoJugadores.add(new Mazo());
		}
		cont = 0;
		for (Integer i = 0; i < (this.jugadores.size()*ReglasNumeroJugadores.cartasARepartir(this.jugadores.size())); i++) {
			mazoJugadores.get(cont).agregarCarta(mazoArriba.getMazo().get(i));
			if ((cont + 1) == this.jugadores.size() ) {
				cont = 0;			
			}
			else {cont++;}
		}		
		for(Integer i = 0; i < this.jugadores.size()*ReglasNumeroJugadores.cartasARepartir(this.jugadores.size()); i++) {
			this.mazoArriba.eliminarCarta(0);
		}		
		return mazoJugadores;
	}

	private void repartirCartaJugadores() { //crea los jugadores y reparte las cartas
		ArrayList<Mazo> mazoJugadores = repartirCartaJugador();
		for (Integer i = 0; i < this.jugadores.size(); i++) {
			jugadores.get(i).setMano(mazoJugadores.get(i).getMazo());
		}
	}

	private boolean cartaArribaMazoAbajo(Carta carta) throws RemoteException {//verifica la carta boca arriba del mazo antes de tirar una carta
		boolean resultado = false;	

		if(this.mazoAbajo.tamanioIgualCero()) {
			resultado = this.mazoAbajo.tamanioIgualCero();
		}		
		else if ((ultimaCartaMazo().getNumero() == carta.getNumero()) || (getPalo() == carta.getPalo()) || (carta.getNumero() == 0) || (ultimaCartaMazo().getNumero() == 0)) {
			resultado = true;
			if ((this.getCartasALevantar() != 0) && (!(ultimaCartaMazo().getNumero() == carta.getNumero()))) {
			resultado = false;	
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
				CartaFuncion.funcionamientoCartas(false, this, cartaAuxiliar);				
			}
		}			
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
				    	   estadoJuegoActualopciones = EstadoJuego.OPCION_SELECCIONADA;	
					       return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
				       }				       
				       else {
							estadoJuegoActualopciones = EstadoJuego.TIRANDO_CARTA;			    	   
				       }
				       return EstadoJuego.TIRANDO_CARTA;
		
		case OPCION_C:	opcionc = false;
						opciond = true;
						if (ultimaCartaMazo().getNumero() == 0 && ultimaCartaMazo().getJodete0()) { //algo no funciona con el 0
							levantarPorJodete(this.getJugadorRequerido());
						}							
						else if(!this.robarCarta(true, getJugadorRequerido())) {
							opcionc = true;
							opciond = false;
						}
						return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;			
		
		case OPCION_D:  this.notificarObservadores(Eventos.BLOQUEAR_JUGADOR_ANTERIOR);
						this.pasarJugador();
						opciond = false;opcionc = true; opcionb = true;							
						if (this.cartaEnJuego.getNumero() == 0 && this.cartaEnJuego.getJodete0()) {
							opcionb = false;
						}
						cartaEnJuego = new Carta(99, Palo.NULL);
						return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
			
		case OPCION_E:  this.cantoJodete();
						return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
						
		case OPCION_F: 	this.opcionf = false; opciond = true;
						this.notificarObservadores(Eventos.TIPOS_DE_PALO);
						return EstadoJuego.CAMBIO_PALO;
						
		case OPCION_G:	if (!this.verificarJodeteAnterior()) {
							this.notificarObservadores(Eventos.NO_CANTO_JODETE_PRIMERO);
						}
						return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
						
		case OPCION_INVALIDA:return EstadoJuego.MOSTRAR_OPCIONES_USUARIO;			
						}
		return estado;
		}
	
	public void pasarJugador() throws RemoteException {
	if (this.terminaRonda()) {
		this.calcularPuntosJugadores();
		this.notificarObservadores(Eventos.MOSTRAR_PUNTOS);
		this.eleminarJugagores();
		if (!this.terminarJuego()) {
			this.cambioJugadorInicial();
			primeraCarta();		
			this.notificarCartas();
		}		
	}
	else{
		this.cambiojugadorActual(true);}		
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
		if (jugadorIni < this.jugadores.size()) {setJugadorInicial(jugadorIni);}
		else {setJugadorInicial(0); jugadorIni = 0;}
		this.mazoArriba.limpiarMazo();
		this.mazoAbajo.limpiarMazo();
		for (Jugador jugador : this.jugadores) {
			jugador.limpiarManoJugador();
		}
		this.mazoArriba.cargarMazo();
		this.repartirCartaJugadores();
		this.setSentidoJuego(1);
		this.setCartasALevantar(0);
		this.setJugadorActual(this.getJugadorInicial());
		this.jugadores.get(jugadorActual).getMano().toString();

		this.notificarObservadores(Eventos.CAMBIAR_RONDA);	
		this.notificarObservadores(Eventos.CAMBIAR_JUGADOR);
		
	}
	
	public void cambiojugadorActual(boolean cambio) throws RemoteException { // cambia al jugador que sigue
		Integer jugadorAct = this.getJugadorActual() + this.getSentidoJuego();
		this.setUltimoJugador(this.getJugadorActual());
		if (jugadorAct == -1) {setJugadorActual(this.jugadores.size() - 1);}
		else if (jugadorAct < this.jugadores.size()) {setJugadorActual(jugadorAct);}
		else {setJugadorActual(0);}	
		this.jugadores.get(this.jugadorActual).setJodete(false);
		if (!this.mazoAbajo.tamanioIgualCero()) {
			if (this.mazoAbajo.obtenerUltimaCarta().getCambioJugador()) {
				this.mazoAbajo.obtenerUltimaCarta().setCambioJugador(false);
				this.cambiojugadorActual(false);
			}
		}
		if (cambio) {this.notificarObservadores(Eventos.CAMBIAR_JUGADOR);notificarCartas();}		
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
	
	public ArrayList<String> mostrarManoJugadorArray(String jugador) throws RemoteException{// muestra las cartas del jugador
		ArrayList<String> cartas = new ArrayList<>();				
			Integer indice = obtenerjugadorNombre(jugador);
			if (indice != -1){			
				for (Carta carta : this.jugadores.get(indice).getMano()) {
				cartas.add(carta.toString());
				}
			}
		
		return cartas;
	}

	public String mostrarCartaMazoAbajo() {
		String carta = "";
		if(!this.mazoAbajo.tamanioIgualCero()) {
			carta = this.mazoAbajo.obtenerUltimaCarta().toString();}
		return (carta);
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
			if(!(this.getCartasALevantar() == 0)) {
				opcionb=false;
				this.setCartasALevantar(getCartasALevantar() - 1);
				if(getCartasALevantar() == 2 || getCartasALevantar() == 1 ) {this.setCartasALevantar(0);}
				this.robarCarta(false, jugador);
			}
		}
		
		if (mostrar) {notificarCartas();}
		return resultado;		
	}

	public void cargarNombreJugadores(String nombre) throws RemoteException {
			this.jugadores.add(new Jugador());
			this.jugadores.get(this.jugadores.size() - 1).setNombre(nombre);	
			if(this.jugadores.size() == 1 ) {
				this.notificarObservadores(Eventos.ESPERANDO_JUGADORES);
			}
			else if(this.jugadores.size() >= 2 ) {
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
		repartirCartaJugadores();
		this.notificarObservadores(Eventos.COMIENZA_EL_JUEGO);
		this.notificarObservadores(Eventos.JUGADOR_INICIAL);
		this.primeraCarta();
		this.notificarObservadores(Eventos.PRIMER_JUGADOR);
	}
	
	private void primeraCarta() throws RemoteException {
		Carta carta = this.mazoArriba.getMazo().get(0);
		this.mazoAbajo.agregarCarta(carta);
		this.mazoArriba.eliminarCarta(0);
		this.setPalo(ultimaCartaMazo().getPalo());
		CartaFuncion.funcionamientoCartas(true, this, carta);
	}

	public void levantarPorJodete(Jugador jugador) throws RemoteException { 
		for (Integer i = 0; i < 5; i++) {
			if (!this.robarCarta(false, jugador)) {
				break;													
			}	
		}
		notificarCartas();
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
	
	private boolean verificarJodeteAnterior() throws RemoteException {
		if (this.getUltimoJugador() == -1) {
			return false;
		}
		else {Jugador ultimoJugador = this.jugadores.get(this.getUltimoJugador());
			if (!(ultimoJugador.getJodete()) && (ultimoJugador.jodete())) {
				levantarPorJodete(ultimoJugador);
				this.notificarObservadores(Eventos.JODETE_LEVENTAS_NO_CANTASTE);
			}
			else {levantarPorJodete(getJugadorRequerido());	
				this.notificarObservadores(Eventos.JODETE_LEVENTAS_MAL_CANTADO);
			}
			return true;
		}
	}

	public String getNombreJugadorActual() {
		return getJugadorRequerido().getNombre();
	}

	public Integer obtenerjugadorNombre(String nombre) {
		for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombre().equals(nombre)) {
                return i;
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
	public Integer getCartasALevantar() {
		return this.cartasALevantar;
	}
	public void setCartasALevantar(Integer c) {
		this.cartasALevantar = c;
	}
	public Integer getSentidoJuego() {
		return sentidoJuego;
	}
	public void setSentidoJuego(Integer sentidoJuego) {
		this.sentidoJuego = sentidoJuego;
	}
	public Integer getNumeroJugadores() throws RemoteException {
		return this.jugadores.size();
	}
	public boolean getOpcionb() {
		return this.opcionb;
	}	
	public void setOpcionb(boolean b) {
		this.opcionb = b;
	}	
	public boolean getOpcionc() {
		return this.opcionc;
	}
	public void setOpcionc(boolean c) {
		this.opcionc = c;
	}
	public boolean getOpciond() {
		return this.opciond;
	}
	public void setOpciond(boolean d) {
		this.opciond = d;
	}
	public boolean getOpcionf() {
		return this.opcionf;
	}
	public void setOpcionf(boolean f) {
		this.opcionf = f;
	}
	public String getUltimoJugadorAgregado() throws RemoteException {
		return this.jugadores.get(jugadores.size()-1).getNombre();
	}
	public void notificarCartas() throws RemoteException {
		this.notificarObservadores(Eventos.CARTAS);
	}
	public void notificarCartaTirada() throws RemoteException {
		this.notificarObservadores(Eventos.CARTA_TIRADA);
	}

	public String getOp()throws RemoteException  {
		return this.op;
	}	
	public void setOp(String string)throws RemoteException  {
		this.op = string;
	}
	public void setCartaEnJuego(ICarta carta) throws RemoteException{
		this.cartaEnJuego = carta;
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
	private void calcularPuntosJugadores() throws RemoteException { // hacerlo de lado del jugador
		for (Integer i = 0; i < this.jugadores.size(); i++) {
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
				this.ultimoJugadorEliminado = jugador.getNombre() + " ";
				this.notificarObservadores(Eventos.JUGADOR_ELIMINADO);
			}			
			this.ultimoJugadorEliminado = "";
		}		
	}
	
	public String getPuntosJugadores() {
		String puntos_jugador = "\n";
		for (Integer i = 0; i < this.jugadores.size(); i++) {
			Jugador jugador = this.jugadores.get(i);
			puntos_jugador = puntos_jugador + jugador.getNombre() + ": "+jugador.getPuntos() + "\n";
		}
		return puntos_jugador;
	}
	
	private void eleminarJugagores() throws RemoteException {		
		int i = 0;	
	    while (i < this.jugadores.size()) {  
	        Jugador jugador = this.jugadores.get(i);
	        if (jugador.getPerdio()) {
	        	if (i <= getJugadorInicial()) {
	        		this.setJugadorInicial(getJugadorInicial() - 1);
	        	if(i < -1) {this.setJugadorInicial(-1);}}
	            this.jugadores.remove(i); 
	        } else {
	            i++; 
	        }        
	    }
	    this.setJugadorActual(0);
	}
	
	public boolean getJugadorEliminado(String jugador) throws RemoteException{
		if (jugador.equals("")) {
			return this.jugadores.get(this.obtenerjugadorNombre(jugador)).getPerdio();	
		}
		else {return false;}

	}
	private boolean terminarJuego() throws RemoteException {
		if (this.jugadores.size() == 1) {
			this.notificarObservadores(Eventos.JUEGO_TERMINADO);
			return true;
		}
		return false;
	}
	public String ultimoJugadorEliminado() throws RemoteException{
		return this.ultimoJugadorEliminado;
	}
}