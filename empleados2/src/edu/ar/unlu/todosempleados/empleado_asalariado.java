package edu.ar.unlu.todosempleados;
import edu.unlu.ar.empleados.empleados_abs;

public class empleado_asalariado extends empleados_abs{
	
	
	public empleado_asalariado(int cuit,String nombre,String apellido, int telefono, double montoacobrar){
		super (cuit,nombre,apellido,telefono,montoacobrar);
		
	}

	
	public double calcularsueldo() {
		return this.MontoACobrar; 
	}
	
	
	
	
}
