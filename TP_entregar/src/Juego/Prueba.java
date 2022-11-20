package Juego;
import ar.edu.unlu.controlador.*;
import ar.edu.unlu.juego.*;

import ar.edu.unlu.vista.*;

public class Prueba {

	public static void main(String[] args) {
		Juego modelo = new Juego();
		VistaConsola vista = new VistaConsola();
		Controlador controlador = new Controlador(modelo, vista);
		
		vista.iniciar();		
		
		
	}

}
