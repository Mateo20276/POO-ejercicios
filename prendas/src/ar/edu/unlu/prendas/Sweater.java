package ar.edu.unlu.prendas;

public class Sweater extends Prendas {

	public Sweater(double preciolista, double porcentajedeganancia) throws Exception {
		super(preciolista, porcentajedeganancia);
		
	}
	
	public double calcularPrecioDeVenta(){
		return super.calcularPrecioDeVenta() + 0.08 * this.getPrecioLista();
	}
	
}
