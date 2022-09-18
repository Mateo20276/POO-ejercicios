package ar.edu.unlu.cuentas;

import ar.edu.unlu.cuentas.Cuenta;
import java.util.Scanner;

public class Cuenta {
	private double saldo;
	private double importeDescubierto;
	private double limiteDescubierto;
	

	public Cuenta(double saldo, double limiteDescubierto){
		this.saldo= saldo;
		this.limiteDescubierto= limiteDescubierto;
		}
	
	public boolean gastarDinero(double monto){
		
		if ((this.saldo-monto) < 0) {

			this.importeDescubierto -= (this.saldo-monto);	
			this.saldo = 0;			
			
		}else {
			this.saldo -= monto;
		}
		
		return true;
	}
	
	public boolean verificarSaldoInsuficiente(double monto) {
		boolean resultado =false;
		
		if ((this.saldo - monto)< 0 ) {
			resultado = true;
		}
		return resultado;
		
	}
	
	public boolean verificarLimiteDescubierto(double monto) {
		boolean resultado =false;
		
		if ((this.importeDescubierto -(this.saldo - monto) <= this.limiteDescubierto)) {
			resultado = true;
		}
		return resultado;
		
	}
	
	
	
	public boolean agregarDinero(double monto) {
		if (this.limiteDescubierto > 0) {
			if ((this.limiteDescubierto - monto) > 0) {
				this.limiteDescubierto -= monto;
			}
			else {
				this.limiteDescubierto = 0;
				this.saldo += monto - this.limiteDescubierto;
			}		
		}
		else {
			this.saldo += monto;
		}
		return true;
	}
	
	
	
	
	public double getSaldo() {
		return this.saldo;	
	}
	
	public double getIporteDescubierto(){
		return this.importeDescubierto;
	}
	
	public double getLimiteDescubierto() {
		return this.limiteDescubierto;
	}
	
}
