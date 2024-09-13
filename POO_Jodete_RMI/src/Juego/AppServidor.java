package Juego;

import java.rmi.RemoteException;

import ar.edu.unlu.juego.Juego;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.servidor.Servidor;

public class AppServidor {
	public static void main(String[] args) {
		Juego modelo = new Juego();
		Servidor servidor = new Servidor("127.0.0.1", 9441);
		try {
			servidor.iniciar(modelo);
		} catch (RemoteException e) {
			System.out.print(e);
		} catch (RMIMVCException e1) {
			System.out.print(e1); 
		}
	
	
	}

}
