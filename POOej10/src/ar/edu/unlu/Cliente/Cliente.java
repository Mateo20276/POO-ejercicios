package ar.edu.unlu.Cliente;

import ar.edu.unlu.cuentas.Cuenta;

import ar.edu.unlu.cuentas.CuentaCredito;
import ar.edu.unlu.cuentas.PlazoFijo;
import java.time.LocalDate;

public class Cliente {
	private Cuenta cuenta;
	private CuentaCredito cuentacredito;
	private PlazoFijo plazofijo;
	private boolean plazofijoestado;

	
	public Cliente(Cuenta cuenta, CuentaCredito cuentacredito) {
		this.cuenta = cuenta;
		this.cuentacredito = cuentacredito;
		this.plazofijoestado = false;
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
	
	public boolean invertirPlazoFijo(double monto, Integer diastotales) {
		boolean resultado= false;
		
		if(!this.plazofijoestado) {
			resultado= true;
			plazofijo = new PlazoFijo(monto, diastotales);
			this.plazofijoestado= true;
		}		

		return resultado;
	}
	
	public boolean retirarDineroPlazoFijo(double monto, LocalDate fecharetiro) {
		boolean resultado = false;
		
		if((this.plazofijoestado) && (this.plazofijo.poderRetirarDinero(fecharetiro))); {
			this.cuenta.agregarDinero(this.plazofijo.retirarDinero(monto));
			this.plazofijoestado = false;
			resultado = true;
		}			

		return resultado;
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
	
	public double getPlazoFijoSaldoInvertido(){
		return this.plazofijo.getSaldoInvertido();
	}

	public LocalDate getPlazoFijoFechaInicial(){
		return this.plazofijo.getFechaInicial();
	}
	
	public Integer getPlazoFijoDiasTotales(){
		return this.plazofijo.getDiasTotales();
	}
	
	public double getPlazoFijoInteres(){
		return PlazoFijo.getInteres();
	}
		
	
}
