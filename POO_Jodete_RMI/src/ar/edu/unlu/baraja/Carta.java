package ar.edu.unlu.baraja;

import java.io.Serializable;

public class Carta implements ICarta, Serializable{
	private int numero;//comodines (numero y palo = 0)
	
	private Palo palo;// (palo 1 = espada), (palo 2 = basto), (palo 3 = oro), (palo 4 = copa).

	private boolean cambioJugador = false; //Cuando se tira un 4. Cuando se tira un 12 con 2 jugadores
	
	private int levantar2Cartas = 0;//Cuando se tira un 2
	
	private boolean jodete0 = false;//cuando cantan jodete con 0
	
	public Carta(int numero, Palo palo) {
		super();
		this.setNumero(numero);
		this.setPalo(palo);		
}

	public String toString() {
		return "Carta [numero=" + numero + ", palo=" + palo + "]";
	}


	public int getNumero() {
		return numero;
	}
	public String getPaloStr() {
		return ""+palo;
	}

	private void setNumero(int numero) {
		this.numero = numero;
	}

	
	public Palo getPalo() {
		return palo;
	}

	public void setPalo(Palo palo) {
		this.palo = palo;
	}
	
	public boolean getCambioJugador() {
		return cambioJugador;
	}

	public void setCambioJugador(boolean cambioJugador) {
		this.cambioJugador = cambioJugador;
	}
	
	public int getLevantar2Cartas() {
		return levantar2Cartas;
	}

	public void sumLevantar2Cartas() {
		if (this.getLevantar2Cartas() == 0) {
			this.levantar2Cartas = levantar2Cartas + 1;
		}
		else {this.levantar2Cartas = levantar2Cartas + 2;};
		
	}
	
	public void restLevantar2Cartas() {
		this.levantar2Cartas = levantar2Cartas - 1;
	}

	public void setJodete0(boolean res){
		this.jodete0 = res;
	}
	
	public boolean getJodete0(){
		return this.jodete0;
	}
	
	
	
}
