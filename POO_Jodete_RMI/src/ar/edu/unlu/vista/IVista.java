package ar.edu.unlu.vista;

import java.rmi.RemoteException;

import ar.edu.unlu.baraja.ICarta;
import ar.edu.unlu.baraja.Palo;
import ar.edu.unlu.controlador.Controlador;

public interface IVista {

	public void menuPrincipal();
	public void setControlador(Controlador controlador);
	public void verCartaMazoAbajo(String cartas, Palo paloj);
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
	public void mostrarCambioColor(Palo palo);
	public void mostrarCantidadJugadoresErronea();
	public void mostrarEsperandoJugadores();
	public void mostrarListosParaComenzar();
	public void obetnerOpcionElegida(String string) throws NumberFormatException, RemoteException;
	public void mostrarOpcionesUsuario(boolean opcionb, boolean opcionc, boolean opciond, boolean opcionf);
	public void seleecionCartaTirar() throws NumberFormatException, RemoteException;
	public void mostrarJugadorAgregado(String nombre, Integer cantidad);
	public void nombreJugador();
	public void mostrarTest(String algo);
	public void mostrarCartaTirada(Integer numero, String palo);
	public void mostrarCantoJodete(Integer i);
	public void mostrarJodete0();
	public void serializar(String jugador);
	public void mostrarFinJuego(String ultimojugador);

}
