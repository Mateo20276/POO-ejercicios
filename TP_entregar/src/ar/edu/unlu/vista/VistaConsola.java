package ar.edu.unlu.vista;

import java.util.Scanner;

import ar.edu.unlu.controlador.*;

public class VistaConsola {

	private Scanner entrada;
	
	private Controlador controlador;
	
	public VistaConsola() {
		this.entrada = new Scanner(System.in);
	}
	
	
	
	public void menuPrincipal() {				
		System.out.println("########################");
		System.out.println("####### JODETE #######");
		System.out.println("########################");
		System.out.println();	
		System.out.println();
		System.out.println("Selecciona una opción:");
		System.out.println("a - Tirar carta");
	}
		
	public void iniciar() {
		boolean salir = false;
		while(!salir) {
			this.menuPrincipal();
			String opcion = this.entrada.nextLine();
			switch (opcion.toLowerCase()) {
				case "a":
					System.out.println("Seleccione carta a tirar");
					String carta = this.entrada.nextLine();
					this.tirarCarta(Integer.parseInt(carta));
					break;
				case "b":;
					break;
				default:
					System.out.println("Opción no válida.");
			}
		}
	}
	
	
	private void tirarCarta(int indice) {
		this.controlador.tirarCarta(indice);
	}
	
	
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	
}
