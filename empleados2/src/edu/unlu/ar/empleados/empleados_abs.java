package edu.unlu.ar.empleados;

abstract public class empleados_abs {
	protected int Cuit;
	protected String Nombre;
	protected String Apellido;
	protected int Telefono;
	protected Double MontoACobrar;
	
	
	public  empleados_abs(int cuit,String nombre,String apellido, int telefono, double montoacobrar) {
		
		this.Cuit = cuit;
		this.Nombre = nombre;
		this.Apellido = apellido; 
		this.Telefono =telefono;
		this.MontoACobrar = montoacobrar;
		
	}
		
	public abstract double calcularsueldo();	
		
		
		
		
				
	}
	

