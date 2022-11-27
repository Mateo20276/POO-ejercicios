package ar.edu.unlu.vista;

import java.util.Scanner;

import ar.edu.unlu.baraja.Carta;
import ar.edu.unlu.baraja.Palo;
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
		boolean opcionb = true;
		boolean opcionc = true;
		boolean opciond = false;
		boolean opcionf = false;

		while (!salir) {
			this.menuInicial();
			String cant = this.entrada.nextLine();
			if (this.cantidadJugadores(Integer.parseInt(cant))) {
				salir = true;

			}
			int cantidad = Integer.parseInt(cant);
			for (int i = 0; i < cantidad; i++) {
				System.out.println("");
				System.out.println("Escriba el nombre del jugador " + (i + 1));
				String nom = this.entrada.nextLine();
				this.controlador.cargarNombreJugadores(nom, i);

			}
		}
			salir = false;
			
		while(!salir) {
			this.menuPrincipal();
			if (opcionb) {	
				System.out.println("b - Tirar carta");
			}
			if(opcionc) {
				System.out.println("c - Robar una carta");
			}
			if (opciond) {
				System.out.println("d - Terminar turno");
			}
			System.out.println("e - Cantar jodete");
			if (opcionf) {
				System.out.println("f - Cambiar de palo");
			}
			String opcion = this.entrada.nextLine();
			if (opcion.equals("b")) {opciond = true;}
			switch (opcion.toLowerCase()) {
				case"a":
					System.out.println(this.verCartas());
					System.out.println(this.verCartaMazoAbajo());
					break;
					
				case "b":
					if(!opcionb){System.out.println("Opción no válida.");break;}
					System.out.println("Seleccione carta a tirar");
					String carta = this.entrada.nextLine();
					opcionb = false;
					opcionc = false;
					Carta cartaax = this.tirarCarta(Integer.parseInt(carta));
					if (cartaax == null) {											
						opcionb = true;
						opcionc = true;
						opciond = false;
						}
					else if (cartaax.getNumero() == 10) {
						opcionb = false;
						opcionc = false;
						opciond = false;
						opcionf = true;
						}
					else if (cartaax.getNumero() == 4) {
						opcionb = true;
						opcionc = true;
						opciond = false;
						opcionf = false;
						}
					break;
					
				case "c":
					if(!opcionc){System.out.println("Opción no válida.");break;}
					opcionc = false;
					opciond = true;
					if(!this.robarCarta()) {
						opcionc = true;
						opciond = false;
					}

					break;
					
				case "d":
					if (!opciond) {System.out.println("Opción no válida.");break;}
					this.pasarJugador();
					opciond = false;
					opcionc = true;
					opcionb = true;
					break;
					
				case "e":
					this.cantarJodete();
					break;
				
				case "f":
					if (!opcionf) {System.out.println("Opción no válida.");break;}
					System.out.println("A que palo desea cambiar (e,o,c,b)");
					String option = this.entrada.nextLine();
					this.cambioPalo(option);
					opcionf = false;
					opciond = true;
					break;
			
				default:
					System.out.println("");
					System.out.println("Opción no válida.");
			}
		}	
	}
	
	

	private void cantarJodete() {
		this.controlador.cantarJodete();
		
	}



	private boolean  robarCarta() {
		return this.controlador.robarCarta();
	}



	private String verCartaMazoAbajo() {
		return this.controlador.verCartaMazoAbajo();
	}



	private boolean  cantidadJugadores(int cant) {
		return this.controlador.cantidadJugadores(cant);
		
	}

	private Carta tirarCarta(int indice) {
		return this.controlador.tirarCarta(indice);
	}
	
	private void pasarJugador() {
		this.controlador.pasarJugador();
	}
	
	private String verCartas(){
		return this.controlador.verCartas();
	}
	
	private void cambioPalo(String p) {
		this.controlador.cambiarPalo(p);
	}
	
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void mostrarCartaInexistente() {
		System.out.println("");
		System.out.println("Carta Inexistente");
		
	}

	public void mostrarCartaNoCoincidente() {
		System.out.println("");
		System.out.println("Carta con numero o palo no valido");
		
	}

	public void mostrarCantidadJugadores(int cantidad) {
		System.out.println("");
		System.out.println("Los jugadores han sido agregados");
		System.out.println("");
		System.out.println("Cantidad jugadores: " + cantidad);
		
	}

	public void mostrarComienzoJuego() {
		System.out.println("");
		System.out.println("El juego ha comenzado");
		
	}



	public void mostrarCambioJugador(String nombre) {
		System.out.println("");
		System.out.println("Fin de turno");
		System.out.println("");
		System.out.println("Turno del jugador " + nombre);

		
	}


	public void mostrarNoSePuedeRobarCartas() {
		System.out.println("");
		System.out.println("Mazo vacio, no se pudieron robar cartas");
	}



	public void mostrarCambioRonda() {
		System.out.println("Una nueva ronda ha comenzado");
	}



	public void mostrarCartaTiradaCorrectamente() {
		System.out.println("Carta tirada correctamente");		
	}

	public void mostrarCartaNormal() {
		System.out.println("Carta normal tirada");
	}
	
	public void mostrarCartaEspecial4() {
		System.out.println("Carta especial 4 tirada");
		
	}
	
	public void mostrarCartaEspecial10() {
		System.out.println("Carta especial 10 tirada");
		
	}


	public void mostrarCaraEspecial11() {
		System.out.println("Carta especial 11 tirada");
	}

	public void mostrarCaraEspecial12() {
		System.out.println("Carta especial 12 tirada");
	}



	public void mostrarCambioColor(Palo palo) {
		System.out.println("El palo a sido cambiado a: " + palo);
		
	}







	
}
