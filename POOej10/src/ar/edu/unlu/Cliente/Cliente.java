package ar.edu.unlu.Cliente;

import ar.edu.unlu.cuentas.Cuenta;
import ar.edu.unlu.cuentas.CuentaCredito;
import ar.edu.unlu.cuentas.PlazoFijo;

public class Cliente {
	private Cuenta cuenta;
	private CuentaCredito cuentacredito;
	private PlazoFijo plazofijo;

	
	public Cliente(Cuenta cuenta, CuentaCredito cuentacredito) {
		this.cuenta= cuenta;
		this.cuentacredito = cuentacredito;
	}

	public Cliente(Cuenta cuenta) {
		this.cuenta= cuenta;
		this.cuentacredito = null;
	}
	
	public boolean gastarDineroCuenta(double monto) {
		return this.cuenta.gastarDinero(monto);
	}
	
	public boolean verificarCuentaSaldoInsuficiente(double monto) {
		return this.cuenta.verificarSaldoInsuficiente(monto);
	}
	
	public boolean verificarCuentaLimiteDescubierto(double monto) {
		return this.cuenta.verificarLimiteDescubierto(monto);
	}
	
	public boolean agregarDineroCuenta(double monto) {
		return this.cuenta.agregarDinero(monto);
	}
	
	public double getCuentaSaldo(){
		return this.cuenta.getSaldo();
	}	
	
	public double getCuentaIporteDescubierto(){
		return this.cuenta.getIporteDescubierto();
	}
	
	public double getCuentaLimiteDescubierto(){
		return this.cuenta.getLimiteDescubierto();
	}
	
	
	
	
	
	
	
	
	
}
