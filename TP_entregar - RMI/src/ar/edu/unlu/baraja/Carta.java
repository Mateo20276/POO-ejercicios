package ar.edu.unlu.baraja;

public class Carta implements ICarta{
	private int numero;//comodines (numero y palo = 0)
	
	private Palo palo;// (palo 1 = espada), (palo 2 = basto), (palo 3 = oro), (palo 4 = copa).

	private Palo cambioPalo = null;
	
	private boolean cambioJugador = false; //Cuando se tira un 4. Cuando se tira un 12 con 2 jugadore
	
	private int levantar2Cartas = 0;//Cuando se tira un 2
	
	public Carta(int numero, Palo palo) {
		super();
		this.setNumero(numero);
		this.setPalo(palo);		
}

	
	@Override
	public String toString() {
		return "Carta [numero=" + numero + ", palo=" + palo + "]";
	}
	

	public Palo cambiarUnPalo(String p) {
		Palo palo = null;
		Palo paloguardar = this.getPalo();
		switch (p.toLowerCase()) {
		case "c":
			palo = Palo.COPA;
			break;
			
		case "o":
			palo = Palo.ORO;
			break;
			
		case "e":
			palo = Palo.ESPADA;
			break;
			
		case "b":
			palo = Palo.BASTO;
			break;
		}
		this.setPalo(palo);
		
		return paloguardar;
		
	}

	public int getNumero() {
		return numero;
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
	
	public void setcambioPalo(Palo palo) {
		this.cambioPalo = palo;
	}
	
	public Palo getcambioPalo() {
		return this.cambioPalo;
	}
	
	
	
}
