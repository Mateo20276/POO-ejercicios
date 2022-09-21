package ar.edu.unlu.prendas;

public class Tarjeta {
	private boolean dorada;
	
	public Tarjeta(boolean esdorada) {
		this.dorada = esdorada;
	}

	public boolean isDorada() {
		return this.dorada;
	}
		
	
	public double calcularDescuento(double total) {
		double dto;
		
		if (isDorada()) {
			dto = 0.015 * total + 100;
			
		}else {
			dto = 0.01 * total;
			
		}
		return dto;
	}

}
