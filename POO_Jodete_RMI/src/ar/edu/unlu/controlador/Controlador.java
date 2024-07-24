package ar.edu.unlu.controlador;

import java.rmi.RemoteException;

import ar.edu.unlu.baraja.ICarta;
import ar.edu.unlu.baraja.Palo;
import ar.edu.unlu.juego.*;
import ar.edu.unlu.observer.*;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.vista.IVista;
import ar.edu.unlu.vista.VistaConsola;


public class Controlador implements IControladorRemoto {
	
	private IVista vista;

	private IJuego modelo;
	
	public Controlador(IVista vista){;
		this.vista = vista;
		this.vista.setControlador(this);

	}
	
	public void actualizar(IObservableRemoto arg0, Object evento) throws RemoteException {
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

	public String verCartas() throws RemoteException {
		return this.modelo.mostrarManoJugador();		
	}

	public void pasarJugador() throws RemoteException {		
			this.modelo.pasarJugador();	
	}

	public boolean cantidadJugadores(int cant) throws RemoteException {
		return this.modelo.cantidadJugadores(cant);
		
	}
	
	public int getCartaEspecial2() throws RemoteException {
		return this.modelo.getCartaEspecial2();
	}

	public String verCartaMazoAbajo() throws RemoteException {
		return this.modelo.mostrarCartaMazoAbajo();
	}
	
	public boolean robarCarta() throws RemoteException{
		return this.modelo.robarCarta(true);
	}

	public void cargarNombreJugadores(String nombre, int i) throws RemoteException {
		this.modelo.cargarNombreJugadores(nombre,i);
	}

	public void cantarJodete() throws RemoteException {
		this.modelo.cantoJodete();
		
	}
		
	public ICarta  tirarCarta(int indice) throws RemoteException {
		return  this.modelo.tirarCarta(indice);
	}
	
	public void cambiarPalo(String p) throws RemoteException {
		this.modelo.cartaComodin10(p);
	}

	@Override
	public <T extends IObservableRemoto> void setModeloRemoto(T arg0) throws RemoteException {
		this.modelo = (IJuego) arg0;
		
	}

}
	
	
	


