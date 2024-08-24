package Juego;

import java.rmi.RemoteException;

import ar.edu.unlu.controlador.Controlador;
import ar.edu.unlu.juego.IJuego;
import ar.edu.unlu.juego.Juego;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.cliente.Cliente;
import ar.edu.unlu.vista.IVista;
import ar.edu.unlu.vista.VistaConsolaSwing;

public class AppCliente {
		public static void main(String[] args) {
		IVista vista1 = new VistaConsolaSwing();
		IVista vista2 = new VistaConsolaSwing();
		//IVista vista3 = new VistaConsolaSwing();
		//IVista vista4 = new VistaConsolaSwing();
		Controlador controlador1 = new Controlador(vista1);
		Controlador controlador2 = new Controlador(vista2);
		//Controlador controlador3 = new Controlador(vista3);
		//Controlador controlador4 = new Controlador(vista4);
		Cliente cliente1 = new Cliente("127.0.0.1", 11121, "127.0.0.1", 9163);// muestra la vista gráfica
		Cliente cliente2 = new Cliente("127.0.0.1", 11122, "127.0.0.1", 9163);// muestra la vista gráfica
		//Cliente cliente3 = new Cliente("127.0.0.1", 11123, "127.0.0.1", 9084);
		//Cliente cliente4 = new Cliente("127.0.0.1", 11124, "127.0.0.1", 9011);// muestra la vista gráfica
		try {
			cliente1.iniciar(controlador1); // enlaza el controlador con el modelo remoto 
			cliente2.iniciar(controlador2); // enlaza el controlador con el modelo remoto 
			//cliente3.iniciar(controlador3);
			//cliente4.iniciar(controlador4);// enlaza el controlador con el modelo remoto 
		} catch (RemoteException e) {
			// error de conexión
		} catch (RMIMVCException e1) {
			// error al crear el objeto de acceso remoto del modelo o del controlador 
		}
	}
}

