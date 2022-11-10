package ar.edu.unlu.baraja;

import java.util.ArrayList;

public class Mazo {
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
		this.agregarCarta(new Carta(0,Palo.COMODIN,49));
		this.agregarCarta(new Carta(0,Palo.COMODIN,50));

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



	public static void main(String[] args) {
		Mazo mazo= new Mazo();
		mazo.generarMazo();
		mazo.mezclar();
		
		System.out.println(mazo);
		
	}
}
