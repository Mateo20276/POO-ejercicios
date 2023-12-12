package ar.edu.unlu.juego;

import ar.edu.unlu.baraja.Carta;

public class CartaFuncion {

	static public void funcionamientoCartas(int numero, Juego juego, Carta carta) {
		switch(numero) {
			case 2:
				juego.notificar(Eventos.CARTA_ESPECIAL_2);
				break;
				
			case 4:
				carta.setCambioJugador(true);	
				juego.notificar(Eventos.CARTA_ESPECIAL_4);
				break;
				
			case 7:
				juego.notificar(Eventos.CARTA_ESPECIAL_7);
				break;
			
			case 10:
				juego.notificar(Eventos.CARTA_ESPECIAL_10);
				break;
				
			case 11:
				juego.notificar(Eventos.CARTA_ESPECIAL_11);
				break;
				
			case 12:
				if (!(numero == 2)) {					
					juego.setSentidoJuego(juego.getSentidoJuego()*(-1));
				}
				else {carta.setCambioJugador(true);}
				juego.notificar(Eventos.CARTA_ESPECIAL_12);
				
				break;
				
			case 0:
				juego.notificar(Eventos.CARTA_ESPECIAL_COMODIN);
				break;
				
			default:
				juego.notificar(Eventos.CARTA_TIRADA_NORMAL);
				break;
		
		}
	}
	
}
