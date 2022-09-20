package ar.edu.unlu.cuentas;
import java.time.LocalDate;
import ar.edu.unlu.dateUtils.DateUtils;

public class PlazoFijo {
	private double saldoInvertido;
	private LocalDate fechaInicial;
	private static final double interes = 0.4;
	private Integer diasTotales;
	
	public PlazoFijo(double monto, Integer diastotales) {
		this.saldoInvertido = monto;
		this.diasTotales = diastotales;
		this.fechaInicial = DateUtils.getFechaDesdeFormato("20-09-2022","FORMATO1");
				
	}
		
	public boolean poderRetirarDinero(LocalDate fecharetiro) {
		boolean resultado = false;
		
		if (DateUtils.esMayor(fechaInicial.plusDays(diasTotales),fecharetiro)) {
			resultado = true;	
		}		

		return resultado;		
	}
	
	public double retirarDinero(double monto) {

		return (this.saldoInvertido + (this.saldoInvertido *interes));		
	}


	public double getSaldoInvertido() {
		return saldoInvertido;
	}


	public LocalDate getFechaInicial() {
		return fechaInicial;
	}


	public Integer getDiasTotales() {
		return diasTotales;
	}


	public static double getInteres() {
		return interes;
	}
	

}
