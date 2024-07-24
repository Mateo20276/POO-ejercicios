package ar.edu.unlu.observer;


public interface Observador {
	public void actualizar(Object evento, Observable observado);
}
