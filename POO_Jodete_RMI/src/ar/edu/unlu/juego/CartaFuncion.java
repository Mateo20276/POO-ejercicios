package ar.edu.unlu.juego;

import java.rmi.RemoteException;

import ar.edu.unlu.baraja.Carta;

public class CartaFuncion {

	static public void funcionamientoCartas(int numero, IJuego juego, Carta carta) throws RemoteException {
		switch(numero) {
			case 2:
				carta.sumLevantar2Cartas() ;	
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_2);
				break;
				
			case 4:
				carta.setCambioJugador(true);	
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_4);
				break;
				
			case 7:
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_7);
				break;
			
			case 10:
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_10);
				break;
				
			case 11:
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_11);
				break;
				
			case 12:
				if (!(juego.getNumeroJugadores() == 2)) {					
					juego.setSentidoJuego(juego.getSentidoJuego()*(-1));
				}
				else {carta.setCambioJugador(true);}
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_12);
				
				break;				
			case 0:
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_COMODIN);
				break;
				
			default:
				juego.notificarObservadores(Eventos.CARTA_TIRADA_NORMAL);
				break;
		
		}
	}
	
}
