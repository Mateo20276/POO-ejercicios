package ar.edu.unlu.prendas;

/**
 * Clase base para implementar un prenda
 * @author Mateo Asenzo
 *
 */

public class Prendas {
	private double porcentajeDeGanancia;
	private double precioLista;
	
	public Prendas(double preciolista, double porcentajedeganancia) throws Exception {
		this.setPorcentajeDeGanancia(porcentajedeganancia);
		this.setPrecioLista(preciolista);
	
	}
	
	public double getPrecioLista() {		
		return this.precioLista;
	}

	public double getPorcentajeDeGanancia() {
		return porcentajeDeGanancia;
	}

	public void setPorcentajeDeGanancia(double porcentajeDeGanancia) throws Exception {
		if(porcentajeDeGanancia < 0) {
			throw new Exception("El porcentaje de ganancia no puede ser menor a 0 ("
									+ String.valueOf(porcentajeDeGanancia) + ")");
			
		}
		else {
			this.porcentajeDeGanancia = porcentajeDeGanancia;
		}
	}

	public void setPrecioLista(double precioLista) {
		this.precioLista = precioLista;
	}
	
	public double calcularPrecioDeVenta() {
		return (((this.getPorcentajeDeGanancia() /100) * this.calcularCosto()) + this.calcularCosto());
	}
	
	public double calcularCosto() {
		return getPrecioLista();
		
	}
	
	
}
