package ar.edu.unlu.baraja;

import java.util.ArrayList;

public class Mazo implements Cloneable {
	private ArrayList<Carta> mazo;

	
	public Mazo() {
		super();
		this.mazo = new ArrayList<>();
	}
	
	public void generarMazo() {
		int c = 1;
		for (Palo palo: Palo.values()) {
			for (int j = 1; j < 13; j++) {
				
				this.agregarCarta(new Carta(j,palo,c++));				
			}
		}
		this.agregarCarta(new Carta(0,null,49));
		this.agregarCarta(new Carta(0,null,50));

	}
	
	
	public ArrayList<Carta> getMazo() {
		return mazo;
	}

	public void setMazo(ArrayList<Carta> mazo) {
		this.mazo = mazo;
	}

	public void mezclar() {
		Carta carta= null;
		for (int i = 0; i < mazo.size(); i++) {
			int random= (int) (Math.random()*mazo.size());
			carta = mazo.get(i);
			mazo.set(i,mazo.get(random));
			mazo.set(random, carta);
			
		}
	}
	
	
	public void agregarCarta(Carta carta) {
		this.mazo.add(carta);
	}
	
	public void eliminarCarta(int indice) {
		this.mazo.remove(indice);
	}

	public void limpiarMazo() {
		if (!(this.mazo.size() == 0)) {
		
			this.mazo.clear();		
		}
		
	}

	public void pasarCartasDeUnMazo(Mazo mazo) {
		mazo.mezclar();
		for (int i = 0; i < mazo.getMazo().size(); i++) {
			this.agregarCarta(mazo.getMazo().get(i));		
		}
		mazo.limpiarMazo();		
	}
	
	public boolean tamanioIgualCero() {
		boolean resultado = false;
		if (this.getMazo().size() == 0){resultado = true;}
				
		return resultado;
	}
			


}

