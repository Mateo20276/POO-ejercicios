package ar.edu.unlu.baraja;

public class Carta {
	private int numero;//comodines (numero y palo = 0)
	
	private int palo;// (palo 1 = espada), (palo 2 = basto), (palo 3 = oro), (palo 4 = copa).

	private int id;
	
	public Carta(int numero, int palo, int id) {
		super();
		this.setNumero(numero);
		this.setPalo(palo);
		this.setId(id);
	}
	
	 	

	public int getNumero() {
		return numero;
	}

	private void setNumero(int numero) {
		this.numero = numero;
	}

	
	public int getPalo() {
		return palo;
	}

	private void setPalo(int palo) {
		this.palo = palo;
	}
	
	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Carta [numero=" + numero + ", palo=" + palo + ", id=" + id + "]";
	}
 
	
}
