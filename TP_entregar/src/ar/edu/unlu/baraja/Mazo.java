package ar.edu.unlu.baraja;

import java.util.ArrayList;

public class Mazo {
	private ArrayList<Carta> mazo;

	
	public Mazo() {
		super();
		this.mazo = new ArrayList<>();
	}
	
	private void generarMazo() {
		int c = 1;
		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 13; j++) {
				
				this.agregarCarta(new Carta(j,i,c++));				
			}
		}
		this.agregarCarta(new Carta(0,0,49));
		this.agregarCarta(new Carta(0,0,50));

	}
	
	
	public ArrayList<Carta> getMazo() {
		return mazo;
	}

	public void setMazo(ArrayList<Carta> mazo) {
		this.mazo = mazo;
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
	
	
	public void agregarCarta(Carta carta) {
		this.mazo.add(carta);
	}
	
	public void eliminarCarta(int indice) {
		this.mazo.remove(indice);
	}

	@Override
	public String toString() {
		return "Mazo [mazo=" + mazo + "]";
	}


	public static void main(String[] args) {
		Mazo mazo= new Mazo();
		mazo.generarMazo();
		mazo.mezclar();
		
		System.out.println(mazo);
		
	}
}
