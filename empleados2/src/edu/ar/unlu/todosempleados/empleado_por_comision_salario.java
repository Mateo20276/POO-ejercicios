package edu.ar.unlu.todosempleados;
import edu.unlu.ar.empleados.empleados_abs;



public class empleado_por_comision_salario extends empleados_abs {
	
	private int VentaTotal;
	private Double PorcentajeVenta;
	
	public empleado_por_comision_salario(int cuit,String nombre,String apellido, int telefono, double montoacobrar, int ventatotal, double porcentajeventa){
		super (cuit,nombre,apellido,telefono,montoacobrar);
		this.VentaTotal = ventatotal;
		this.PorcentajeVenta = porcentajeventa;
		
	}

	
	public double calcularsueldo() {
		return (this.getVentaTotal()*this.getMontoACobrar()*this.getPorcentajeVenta()/100);
		
	}


	public int getVentaTotal() {
		return VentaTotal;
	}


	public Double getPorcentajeVenta() {
		return PorcentajeVenta;
	}
	
	public Double getMontoACobrar() {
		return this.MontoACobrar;
	}

}
