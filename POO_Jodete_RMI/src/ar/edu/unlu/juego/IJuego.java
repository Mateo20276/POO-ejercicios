package ar.edu.unlu.juego;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ar.edu.unlu.baraja.Carta;
import ar.edu.unlu.baraja.ICarta;
import ar.edu.unlu.baraja.Mazo;
import ar.edu.unlu.baraja.Palo;
import ar.edu.unlu.jugador.Jugador;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.vista.EstadoJuego;

public interface IJuego extends IObservableRemoto, Serializable{

	ICarta tirarCarta(Integer indice) throws RemoteException;
	boolean terminaRonda() throws RemoteException;
	void cambioJugadorInicial() throws RemoteException;
	void cambiojugadorActual(boolean cambio) throws RemoteException;
	void cartaComodin10(String p) throws RemoteException;
	String mostrarManoJugador(String jugador) throws RemoteException;
	String mostrarCartaMazoAbajo() throws RemoteException;
	void pasarJugador() throws RemoteException;
	boolean robarCarta(boolean mostrar, Jugador jugador) throws RemoteException;
	void cargarNombreJugadores(String nombre) throws RemoteException;
	void levantarPorJodete(Jugador jugador) throws RemoteException;
	void cantoJodete() throws RemoteException;
	String getNombreJugadorActual() throws RemoteException;
	Mazo getMazoAbajo() throws RemoteException;
	Integer getJugadorInicial() throws RemoteException;
	void setJugadorInicial(Integer jugadorInicial) throws RemoteException;
	Integer getSentidoJuego() throws RemoteException;
	void setSentidoJuego(Integer sentidoJuego) throws RemoteException;
	Integer getNumeroJugadores() throws RemoteException;
	//Integer getCartaEspecial2() throws RemoteException;
	void comenzarJuego() throws RemoteException;
	void jugadorAJugar() throws RemoteException;
	void seleccionarOpcion(String opcion) throws RemoteException;
	boolean getOpcionb() throws RemoteException;
	boolean getOpcionc() throws RemoteException;	
	boolean getOpciond() throws RemoteException;	
	boolean getOpcionf() throws RemoteException;	
	EstadoJuego opcionesDeJuego(EstadoJuego estado, String cartanum) throws NumberFormatException, RemoteException;
	public String getOp() throws RemoteException;
	public String getUltimoJugadorAgregado() throws RemoteException;
	Integer getCartaEnJuegoNumero() throws RemoteException;
	String getCartaEnJuegoPalo()throws RemoteException;
	String getalgo() throws RemoteException;
	void setOp(String string)throws RemoteException;
	Palo getPalo()throws RemoteException;
	boolean getJugadorEliminado(String jugador) throws RemoteException;
	String ultimoJugadorEliminado() throws RemoteException;
	ArrayList<String> mostrarManoJugadorArray(String jugador) throws RemoteException;
	void setOpcionb(boolean b) throws RemoteException;
	void setOpcionc(boolean c) throws RemoteException;
	void setOpciond(boolean d) throws RemoteException;
	void setOpcionf(boolean f) throws RemoteException;
	Integer getCartasALevantar()throws RemoteException;
	void setCartasALevantar(Integer i)throws RemoteException;
	void notificarCartas() throws RemoteException;
	String getPuntosJugadores() throws RemoteException;
	void notificarCartaTirada() throws RemoteException;
	void setCartaEnJuego(ICarta carta) throws RemoteException;

}