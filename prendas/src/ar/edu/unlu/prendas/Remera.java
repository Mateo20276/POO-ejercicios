package ar.edu.unlu.prendas;

public class Remera extends Prendas {
	private double impuesto;
	
	public Remera(double preciolista, double porcentajedeganancioa, double impuesto) throws Exception {
		super(preciolista, porcentajedeganancioa);
		

	}


	public void setimpuesto(double impuesto) throws Exception {
		if (impuesto < 0) {
			throw new Exception("El impuesto no puede ser menor a 0 ("
					+ String.valueOf(impuesto) + ")");
			}
		this.impuesto = impuesto;
		
	}
	
	public double getImpuesto() {
		return this.impuesto;
	}

	//sobreescribiendo metodo de prenda
	public double calcularCosto() {
		return (super (calcularCosto())+ this.getImpuesto());
	}


}
