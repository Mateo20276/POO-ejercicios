package ar.edu.unlu.controlador;

import java.rmi.RemoteException;

import ar.edu.unlu.baraja.ICarta;
import ar.edu.unlu.baraja.Palo;
import ar.edu.unlu.juego.*;
import ar.edu.unlu.observer.*;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.vista.EstadoJuego;
import ar.edu.unlu.vista.IVista;


public class Controlador implements IControladorRemoto {
	
	private IVista vista;
	private IJuego modelo;	
	private String jugador = "";	
	public Controlador(IVista vista){;
		this.vista = vista;
		this.vista.setControlador(this);

	}
	
	public void actualizar(IObservableRemoto arg0, Object evento) throws RemoteException {
		if(evento instanceof Eventos) {
			String ultimojugador = this.modelo.getUltimoJugadorAgregado();
			String jugadoractual = this.modelo.getNombreJugadorActual();
			switch((Eventos) evento) {
			case CARTAS:
				if (!getJugador().equals("")) {
					String cartas = this.modelo.mostrarManoJugador(getJugador());
					this.vista.verCartas(cartas);
				}
				String cartas = this.modelo.mostrarCartaMazoAbajo();
				this.vista.verCartaMazoAbajo(cartas);
				break;
			case CARTA_INEXISTENTE:
				this.vista.mostrarCartaInexistente();
				break;			
			case JUGADOR_INICIAL:
				this.vista.mostrarCambioJugador(jugadoractual);			
				break;				
			case CARTA_NO_COINCIDENTE:
				this.vista.mostrarCartaNoCoincidente();
				break;				
			case COMIENZA_EL_JUEGO:
				this.vista.mostrarComienzoJuego();
				break;				
			case ESPERANDO_JUGADORES:
				this.vista.mostrarJugadorAgregado(ultimojugador, this.modelo.getNumeroJugadores());
				if (!getJugador().equals("")) {				
					this.vista.mostrarEsperandoJugadores();
					}
				else {
					this.vista.nombreJugador();
				}
				break;				
			case LISTOS_PARA_COMENZAR:
				this.vista.mostrarJugadorAgregado(ultimojugador, this.modelo.getNumeroJugadores());
				if (!getJugador().equals("")) {				
					this.vista.mostrarListosParaComenzar();
					}
				else {
					this.vista.nombreJugador();
				}
				break;				
			case SELECCIONAR_CARTA_A_TIRAR:
				if (jugadoractual.equals(getJugador())) {
					this.vista.seleecionCartaTirar();
				}
				break;				
			case CAMBIAR_JUGADOR:
				this.vista.mostrarFinTurno();
				this.vista.mostrarCambioJugador(jugadoractual);			
				break;
			
			case MOSTRAR_OPCIONES:
				if (jugadoractual.equals(getJugador())) {
					this.vista.mostrarOpcionesUsuario(this.modelo.getOpcionb(),this.modelo.getOpcionc(),this.modelo.getOpciond(),this.modelo.getOpcionf());
				}
				break;				
			case SELECCIONAR_OPCIONES:
				if (jugadoractual.equals(this.getJugador())) {
					String opc = this.modelo.getOp();
					this.modelo.setOp("");
					this.vista.obetnerOpcionElegida(opc);
				}			
				break;
			case CANTO_JODETE:
				this.vista.mostrarCantoJodete(1);
				break;
			case JODETE_LEVENTAS_MAL_CANTADO:
				this.vista.mostrarCantoJodete(2);
				break;
			case JODETE_LEVENTAS_NO_CANTASTE:
				this.vista.mostrarCantoJodete(3);
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
			case CARTA_TIRADA:
				this.vista.mostrarCartaTirada(this.modelo.getCartaEnJuegoNumero(), this.modelo.getCartaEnJuegoPalo());
				break;
			case CAMBIO_COLOR:
				Palo palo = this.modelo.getMazoAbajo().obtenerUltimaCarta().getPalo();
				this.vista.mostrarCambioColor(palo);
				break;				
			case CANTIDAD_JUGADORES_ERRONEA:
				this.vista.mostrarCantidadJugadoresErronea();
			case CARTA_ESPECIAL_COMODIN:
				break;
			case TEST:
				String algo = this.modelo.getalgo();
				this.vista.mostrarTest(algo);
				break;
			default:
				break;

			}
		}	
	}

	private String getJugador() {
		return jugador;
	}
	
	public void setJugador(String jugador) {
		this.jugador = jugador;
	}

	public String verCartaMazoAbajo() throws RemoteException {
		return this.modelo.mostrarCartaMazoAbajo();
	}

	public void cargarNombreJugadores(String nombre) throws RemoteException {
		this.modelo.cargarNombreJugadores(nombre);
	}

	public <T extends IObservableRemoto> void setModeloRemoto(T arg0) throws RemoteException {
		this.modelo = (IJuego) arg0;		
	}

	public void comenzarJuego() throws RemoteException {
		this.modelo.comenzarJuego();		
	}

	public void mostrarOpcionesUsuarioJugando() throws RemoteException {
		this.modelo.jugadorAJugar();		
	}

	public void seleccionarOpcion(String opcion) throws RemoteException {
		this.modelo.seleccionarOpcion(opcion);		
	}

	public EstadoJuego opcionesDeJuego(EstadoJuego estadoJuegoSegunSeleccion, String cartanum) throws NumberFormatException, RemoteException {
		return this.modelo.opcionesDeJuego(estadoJuegoSegunSeleccion,cartanum);		
	}
}