package ar.edu.unlu.vista;

import ar.edu.unlu.baraja.ICarta;
import ar.edu.unlu.baraja.Palo;
import ar.edu.unlu.controlador.Controlador;

public interface IVista {

	public void menuPrincipal();
	public void menuInicial();
	public void cantarJodete();
	public boolean  robarCarta();
	public boolean  cantidadJugadores(int cant);
	public ICarta tirarCarta(int indice);
	public void pasarJugador();
	public void cambioPalo(String p);
	public void setControlador(Controlador controlador);
	public int getCartaEspecial2();
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
