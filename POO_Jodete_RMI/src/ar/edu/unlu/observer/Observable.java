package ar.edu.unlu.observer;


public interface Observable {
	public void notificar(Object evento);
	
	public void agregadorObservador(Observador observador);

}
