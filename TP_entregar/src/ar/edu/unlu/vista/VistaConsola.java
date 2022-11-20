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
		System.out.println("a - Mirar cartas");
		System.out.println("b - Tirar carta");
	}
	
	public void menuInicial() {
		System.out.println("########################");
		System.out.println("####### JODETE #######");
		System.out.println("########################");
		System.out.println();	
		System.out.println();
		System.out.println("Escriba la cantidad de jugadores (2 a 6)");
		
			
		
	}
		
	public void iniciar() {
		boolean salir = false;
		boolean pasa = false;
		while (!salir) {
			this.menuInicial();
			String cant = this.entrada.nextLine();
			if (this.cantidadJugadores(Integer.parseInt(cant))) {
				salir = true;

			}
		}
			salir = false;
			
		while(!salir) {
			this.menuPrincipal();
			if (pasa) {
				System.out.println("c - Terminar turno");
			}
			String opcion = this.entrada.nextLine();
			if (opcion.equals("b")) {pasa = true;}
			switch (opcion.toLowerCase()) {
				case"a":
					System.out.println(this.verCartas());
					break;
					
				case "b":
					System.out.println("Seleccione carta a tirar");
					String carta = this.entrada.nextLine();
					this.tirarCarta(Integer.parseInt(carta));
					break;
					
				case "c":;
					this.pasarJugador();
					pasa = false;
					break;
					
				default:
					System.out.println("Opción no válida.");
			}
		}	
	}
	
	
	private boolean  cantidadJugadores(int cant) {
		return this.controlador.cantidadJugadores(cant);
		
	}

	private void tirarCarta(int indice) {
		this.controlador.tirarCarta(indice);
	}
	
	private void pasarJugador() {
		this.controlador.pasarJugador();
	}
	
	private String verCartas(){
		return this.controlador.verCartas();
	}
	
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}



	public void mostrarCartaInexistente() {
		System.out.println("Carta Inexistente");
		
	}
	
}
