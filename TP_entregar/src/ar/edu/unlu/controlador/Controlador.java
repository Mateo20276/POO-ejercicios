package ar.edu.unlu.controlador;

import ar.edu.unlu.baraja.ICarta;
import ar.edu.unlu.baraja.Palo;
import ar.edu.unlu.juego.*;
import ar.edu.unlu.observer.*;
import ar.edu.unlu.vista.IVista;
import ar.edu.unlu.vista.VistaConsola;


public class Controlador implements Observador {
	
	private IVista vista;

	private Juego modelo;
	
	public Controlador(Juego juego, IVista vista){
		this.modelo = juego;
		this.vista = vista;
		this.vista.setControlador(this);
		this.modelo.agregadorObservador(this);

	}
			
	@Override
	public void actualizar(Object evento, Observable observado) {

		if(evento instanceof Eventos) {
			switch((Eventos) evento) {
			case CARTAS:
				String cartas = this.modelo.mostrarManoJugador();
				this.vista.verCartas(cartas);
				cartas = this.modelo.mostrarCartaMazoAbajo();
				this.vista.verCartaMazoAbajo(cartas);
				break;
			case CARTA_INEXISTENTE:
				this.vista.mostrarCartaInexistente();
				break;
			
			case JUGADOR_INICIAL:
				String nombre = this.modelo.getNombreJugadorActual();
				this.vista.mostrarCambioJugador(nombre);			
				break;
				
			case CARTA_NO_COINCIDENTE:
				this.vista.mostrarCartaNoCoincidente();
				break;
			
			case CANTIDAD_JUGADORES:
				int cantidad = this.modelo.getNumeroJugadores();
				this.vista.mostrarCantidadJugadores(cantidad);
				break;
				
			case COMIENZA_EL_JUEGO:
				this.vista.mostrarComienzoJuego();
				break;
			
			case CAMBIAR_JUGADOR:
				String nombre1 = this.modelo.getNombreJugadorActual();
				this.vista.mostrarFinTurno();
				this.vista.mostrarCambioJugador(nombre1);			
				break;
			
			case NO_ES_POSIBLE_ROBAR_CARTAS:
				this.vista.mostrarNoSePuedeRobarCartas();
				break;
			
			case CAMBIAR_RONDA:
				this.vista.mostrarCambioRonda();
				break;
				
			case CARTA_TIRADA_CORRECTAMENTE:	
				this.vista.mostrarCartaTiradaCorrectamente();
				break;

				
			case CARTA_TIRADA_NORMAL:
				this.vista.mostrarCartaNormal();
				break;
				
			case CARTA_ESPECIAL_4:
				this.vista.mostrarCartaEspecial4();
				break;
				
			case CARTA_ESPECIAL_7:
				this.vista.mostrarCartaEspecial7();
				break;
				
			case CARTA_ESPECIAL_10:
				this.vista.mostrarCartaEspecial10();
				break;
				
			case CARTA_ESPECIAL_11:
				this.vista.mostrarCaraEspecial11();
				break;
				
			case CARTA_ESPECIAL_12:
				this.vista.mostrarCaraEspecial12();
				break;
			case CAMBIO_COLOR:
				Palo palo = this.modelo.getMazoAbajo().obtenerUltimaCarta().getPalo();
				this.vista.mostrarCambioColor(palo);
				break;
				
			case CANTIDAD_JUGADORES_ERRONEA:
				this.vista.mostrarCantidadJugadoresErronea();
			case CARTA_ESPECIAL_2:
				break;
			case CARTA_ESPECIAL_COMODIN:
				break;
			default:
				break;

			}
		}
	}

	public String verCartas() {
		return this.modelo.mostrarManoJugador();		
	}

	public void pasarJugador() {		
		this.modelo.pasarJugador(); //TODO		
	}

	public boolean cantidadJugadores(int cant) {
		return this.modelo.cantidadJugadores(cant);
		
	}
	
	public int getCartaEspecial2() {
		return this.modelo.getCartaEspecial2();
	}

	public String verCartaMazoAbajo() {
		return this.modelo.mostrarCartaMazoAbajo();
	}
	
	public boolean robarCarta(){
		return this.modelo.robarCarta(true);
	}

	public void cargarNombreJugadores(String nombre, int i) {
		this.modelo.cargarNombreJugadores(nombre,i);
	}

	public void cantarJodete() {
		this.modelo.cantoJodete();
		
	}
		
	public ICarta  tirarCarta(int indice) {
		return  this.modelo.tirarCarta(indice);
	}
	
	public void cambiarPalo(String p) {
		this.modelo.cartaComodin10(p);
	}

}
	
	
	


