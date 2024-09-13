package ar.edu.unlu.juego;

import java.rmi.RemoteException;

import ar.edu.unlu.baraja.Carta;

public class CartaFuncion {

	static public void funcionamientoCartas(boolean inicio, IJuego juego, Carta carta) throws RemoteException {
		switch(carta.getNumero()) {
			case 2:
				carta.sumLevantar2Cartas() ;	
				juego.setCartasALevantar(juego.getCartasALevantar() + 2);
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_2);
				break;
				
			case 4:
				if (inicio) {
					juego.cambiojugadorActual(false);
				}
				else {
					carta.setCambioJugador(true);
				}	
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_4);
				break;
				
			case 7:
				if (!inicio) {
				juego.setOpcionb(true);
				juego.setOpcionc(false);
				juego.setOpciond(true);}

				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_7);
				break;
			
			case 10:
				juego.setOpcionb(false);
				juego.setOpcionc(false);
				juego.setOpciond(false);
				juego.setOpcionf(true);
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_10);
				break;
				
			case 11:
				if (!inicio) {
				juego.setOpcionb(true);
				juego.setOpcionc(false);
				juego.setOpciond(true);}
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_11);
				break;
				
			case 12:
				if (!(juego.getNumeroJugadores() == 2)) {					
					juego.setSentidoJuego(juego.getSentidoJuego()*(-1));
				}
				else {carta.setCambioJugador(true);}
				if (inicio) {
					juego.pasarJugador();
				}
				juego.notificarObservadores(Eventos.CARTA_ESPECIAL_12);
				
				break;				
			default:
				juego.notificarObservadores(Eventos.CARTA_TIRADA_NORMAL);
				break;
		
		}
		juego.setCartaEnJuego(carta);
		juego.notificarCartaTirada();
		juego.notificarCartas();
	}
	
}
