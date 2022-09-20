package ar.edu.unlu.dateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateUtils {

	public static final String FORMATO1 = "dd-MM-yyyy";

	public static final String FORMATO2 = "MM-dd-yyyy";

	private static DateTimeFormatter formaterFormato1 = DateTimeFormatter.ofPattern(FORMATO1);

	private static DateTimeFormatter formaterFormato2 = DateTimeFormatter.ofPattern(FORMATO2);


	public static LocalDate getFechaDesdeFormato(String fecha, String formato) {

		if(formato.equals(FORMATO1)) {

			return LocalDate.parse(fecha, formaterFormato1);

		}else if (formato.equals(FORMATO2)) {

			return LocalDate.parse(fecha, formaterFormato2);

		}

		return null;

	}

	
	public static boolean esMayor(LocalDate a, LocalDate b) {

		return a.compareTo(b) > 0;

	}

	
	public static boolean esMenor(LocalDate a, LocalDate b) {

		return a.compareTo(b) < 0;

	}

	
	public static boolean estaEntreFechas(LocalDate aComparar, LocalDate minRango, LocalDate maxRango) {

		return esMenor(minRango, aComparar) && esMenor(aComparar, maxRango);

	}

}
