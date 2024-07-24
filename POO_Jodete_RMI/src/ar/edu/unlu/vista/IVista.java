package ar.edu.unlu.vista;

import java.rmi.RemoteException;

import ar.edu.unlu.baraja.ICarta;
import ar.edu.unlu.baraja.Palo;
import ar.edu.unlu.controlador.Controlador;

public interface IVista {

	public void menuPrincipal();
	public void menuInicial();
	public void cantarJodete() throws RemoteException;
	public boolean  robarCarta() throws RemoteException;
	public boolean  cantidadJugadores(int cant) throws RemoteException;
	public ICarta tirarCarta(int indice) throws RemoteException;
	public void pasarJugador() throws RemoteException;
	public void cambioPalo(String p) throws RemoteException;
	public void setControlador(Controlador controlador);
	public int getCartaEspecial2() throws RemoteException;
	public void verCartaMazoAbajo(String cartas);
	public void verCartas(String cartas);
	public void mostrarCartaInexistente();
	public void mostrarCartaNoCoincidente();
	public void mostrarCantidadJugadores(int cantidad);
	public void mostrarComienzoJuego();
	public void mostrarFinTurno();
	public void mostrarCambioJugador(String nombre);
	public void mostrarNoSePuedeRobarCartas();
	public void mostrarCambioRonda();
	public void mostrarCartaTiradaCorrectamente();
	public void mostrarCartaNormal();
	public void mostrarCartaEspecial4();
	public void mostrarCartaEspecial7();
	public void mostrarCartaEspecial10();
	public void mostrarCaraEspecial11();
	public void mostrarCaraEspecial12();
	public void mostrarCambioColor(Palo palo);
	public void mostrarCantidadJugadoresErronea();
	
	
}
