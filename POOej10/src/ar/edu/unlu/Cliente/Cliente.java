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
	


}
