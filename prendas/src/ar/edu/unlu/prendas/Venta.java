package ar.edu.unlu.prendas;

import java.util.List;

public class Venta {

	private List<Prendas> prendas;
	private Tarjeta tarjeta;
	
	public Venta(List <Prendas> prendas) {
		
		this.prendas = prendas;
		
	}
	
	public Venta(List <Prendas> prendas, Tarjeta tarjeta) {
		this(prendas);
		this.tarjeta= tarjeta;
		
		
	}
	
	public double calcularToral() {
		double total= 0;
		for(Prendas prenda: prendas) {
			total += prenda.calcularPrecioDeVenta();
		}
		
		if(this.esConTarjeta()) {
			total -= this.tarjeta.calcularDescuento(total);
		}
		
		return total;
	}

	public boolean esConTarjeta() {
		return this.tarjeta != null;
		
	}


}

