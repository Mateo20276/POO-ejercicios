package ar.edu.unlu.cuentas;

public class CuentaCredito {
	private double compras;
	private double intereses;
	private double limiteGasto;;
	private static final double recargo = 0.05;

	public CuentaCredito(double limiteGasto) {
		this.limiteGasto = limiteGasto;
	}
	
	public boolean comprar(double monto) {
		boolean resultado = false;
		
		if ((monto - this.limiteGasto) > 0) {
			
			this.compras += monto;
			this.limiteGasto -= monto;
			this.intereses += (monto * recargo);
			resultado = true;
		}
		
		return resultado;
	}
	
	public double pagosCompras(double monto) {
		double comprasaux;
		if (monto > this.compras) {
			comprasaux = this.compras;
			this.compras = 0.0d;
			this.limiteGasto += comprasaux;
			return (monto - comprasaux);
			
		}
		else {
			this.compras-= monto;
			this.limiteGasto += monto;
			return 0.0d;
		}
	}
		
		
	public double pagosIntereses(double monto) {
		double comprasaux;
			if (monto > this.intereses) {
			comprasaux = this.intereses;
			this.intereses = 0.0d;
			return (monto - comprasaux);
			
		}
		else {
			this.intereses-= monto;
			return 0.0d;
		}

	}
	
	
	public double getCompras() {
		return this.compras;
	
	}
	
	public double getIntereses() {
		return this.intereses;
		
	}
	
	public double getLimitegasto() {
		return this.limiteGasto;
		
	}
	
	public double getRecargo() {
		return recargo;
		
	}
	
}
