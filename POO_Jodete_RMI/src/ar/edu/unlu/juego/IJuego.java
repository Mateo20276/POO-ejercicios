package ar.edu.unlu.juego;

import java.io.Serializable;
import java.rmi.RemoteException;

import ar.edu.unlu.baraja.ICarta;
import ar.edu.unlu.baraja.Mazo;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

public interface IJuego extends IObservableRemoto, Serializable{

	boolean cantidadJugadores(Integer cant) throws RemoteException;

	ICarta tirarCarta(Integer indice) throws RemoteException;

	boolean terminaRonda() throws RemoteException;

	void cambioJugadorInicial() throws RemoteException;

	void cambiojugadorActual(boolean cambio) throws RemoteException;

	void cartaComodin10(String p) throws RemoteException;

	String mostrarManoJugador() throws RemoteException;

	String mostrarCartaMazoAbajo() throws RemoteException;

	void pasarJugador() throws RemoteException;

	boolean robarCarta(boolean mostrar) throws RemoteException;

	void cargarNombreJugadores(String nombre, Integer i) throws RemoteException;

	void cantarJodete() throws RemoteException;

	void cantoJodete() throws RemoteException;

	String getNombreJugadorActual() throws RemoteException;

	Mazo getMazoAbajo() throws RemoteException;

	Integer getJugadorInicial() throws RemoteException;

	void setJugadorInicial(Integer jugadorInicial) throws RemoteException;


	Integer getSentidoJuego() throws RemoteException;

	void setSentidoJuego(Integer sentidoJuego) throws RemoteException;

	Integer getNumeroJugadores() throws RemoteException;

	Integer getCartaEspecial2() throws RemoteException;
}