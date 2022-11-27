package ar.edu.unlu.baraja;

public class Carta{
	private int numero;//comodines (numero y palo = 0)
	
	private Palo palo;// (palo 1 = espada), (palo 2 = basto), (palo 3 = oro), (palo 4 = copa).

	private int id;
	
	private Palo cambioPalo = null;
		
	public Carta(int numero, Palo palo, int id) {
		super();
		this.setNumero(numero);
		this.setPalo(palo);
		this.setId(id);
		
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
	
	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}
	
	public void setcambioPalo(Palo palo) {
		this.cambioPalo = palo;
	}
	
	public Palo getcambioPalo() {
		return this.cambioPalo;
	}
	@Override
	public String toString() {
		return "[numero=" + numero + ", palo=" + palo + ", id=" + id + "]" + "\n";
	}
 
	
}
