package ar.edu.unlu.poo.todoapp.controlador;

import java.util.List;

import ar.edu.unlu.poo.todoapp.modelo.Eventos;
import ar.edu.unlu.poo.todoapp.modelo.ITarea;
import ar.edu.unlu.poo.todoapp.modelo.ListadoDeTareas;
import ar.edu.unlu.poo.todoapp.vista.VistaConsola;
import ar.edu.unlu.poo.utils.observer.Observable;
import ar.edu.unlu.poo.utils.observer.Observador;

public class Controlador implements Observador{
	private ListadoDeTareas modelo;
	
	private VistaConsola vista;
	
	public Controlador(ListadoDeTareas modelo, VistaConsola vista) {
		this.modelo = modelo;
		this.vista = vista;
		this.vista.setControlador(this);
		this.modelo.agregadorObservador(this);
	}

	public void agregarTarea(String titulo, String descripcion) {
		this.modelo.agregarTarea(titulo, descripcion);
	}
	
	public void eliminarTarea(String titulo) {
		this.modelo.eliminarTarea(titulo);
	}

	@Override
	public void actualizar(Object evento, Observable observador) {
		if(evento instanceof Eventos) {
			switch((Eventos) evento) {
				case TAREA_AGREGADA:
					List<ITarea> tareas = this.modelo.listarTareas();//Preguntar evento en VistaConsola?
					this.vista.mostrarTareas(tareas);
					break;
					
				case TAREA_ELIMINADA:
					List<ITarea> tareas1 = this.modelo.listarTareas();
					this.vista.mostrarTareas(tareas1);
					break;
					
				case TAREA_NO_ELIMINADA:
					List<ITarea> tareas2 = this.modelo.listarTareas();
					this.vista.mostrarTareas(tareas2);
					break;
			}
		}
	}

}
