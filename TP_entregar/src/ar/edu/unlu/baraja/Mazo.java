package ar.edu.unlu.baraja;

import java.util.ArrayList;

public class Mazo implements Cloneable {
	private ArrayList<Carta> mazo;

	
	public Mazo() {
		super();
		this.mazo = new ArrayList<>();
	}
	
	private void generarMazo() {
		for (Palo palo: Palo.values()) {
			for (int j = 1; j < 13; j++) {
				
				this.agregarCarta(new Carta(j,palo));				
			}
		}
		this.agregarCarta(new Carta(0,null));
		this.agregarCarta(new Carta(0,null));

	}
	
	private void mezclar() {
		Carta carta= null;
		for (int i = 0; i < mazo.size(); i++) {
			int random= (int) (Math.random()*mazo.size());
			carta = mazo.get(i);
			mazo.set(i,mazo.get(random));
			mazo.set(random, carta);
			
		}
	}
	public int tamanioMazo(){
		int tamanio = this.getMazo().size();
		
		return tamanio;
		
	}
	public void cargarMazo() {
		this.generarMazo();
		this.mezclar();
	}
	
	public Carta obtenerCarta(int i) {
		Carta carta = this.getMazo().get(i);
		return carta;
	}
	

	public void pasarCartasDeUnMazo(Mazo mazo) {
		Carta carta = null;
		for (int i = 0; i < mazo.getMazo().size() - 1; i++) {
			this.agregarCarta(mazo.getMazo().get(i));		
		}
		this.mezclar();
		carta = mazo.getMazo().get(mazo.getMazo().size());
		mazo.limpiarMazo();		
		mazo.agregarCarta(carta);
	}
	
	@Override
	public String toString() {
		return "Mazo [mazo=" + mazo + "]";
	}

	public boolean tamanioIgualCero() {
		return (this.tamanioMazo() == 0);

	}
	
	public Carta obtenerUltimaCarta() {
		return (this.obtenerCarta(this.getMazo().size()-1));
	}

	public ArrayList<Carta> getMazo() {
		return mazo;
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

}

