package ar.edu.unlu.juego;

public class ReglasNumeroJugadores {
	
	public static int cartasARepartir(int numeroJugadores) {
		int numeroDeCartas = 0;
			
		if (numeroJugadores == 2){numeroDeCartas = 7;}
		else if (numeroJugadores == 3){numeroDeCartas = 6;}
		else if (numeroJugadores == 4){numeroDeCartas = 5;}
		else if (numeroJugadores == 5 || numeroJugadores == 6){numeroDeCartas = 4;}
		else if (numeroJugadores == 23) {numeroDeCartas = 2;}
		
		return numeroDeCartas;
	}
}
