package ar.edu.unlu.vista;


import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import ar.edu.unlu.baraja.Palo;
import ar.edu.unlu.controlador.Controlador;
import ar.edu.unlu.serializacion.*;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class VistaConsolaSwing implements IVista {

	private JFrame frame;
	private JTextField textInput;
	private final JButton btnBoton = new JButton("Aceptar");
	private JTextArea textVista;
	private Controlador controlador;
	private Estados estadoActual = Estados.JUEGO_OPCION_1_2;
	private EstadoJuego estadoJuegoActual = EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
	private String cartaTirar = "";
	private static Serializador serializador=new Serializador("src/datos.dat");

	public void println(String texto) {
		textVista.append(texto + "\n");	
	}
	
	public void println() {
		println("");
	}
	
	public VistaConsolaSwing() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 823, 615);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {FormSpecs.RELATED_GAP_COLSPEC,ColumnSpec.decode("max(31dlu;default):grow"),FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,FormSpecs.DEFAULT_COLSPEC,FormSpecs.RELATED_GAP_COLSPEC,ColumnSpec.decode("max(12dlu;default)"),FormSpecs.RELATED_GAP_COLSPEC,ColumnSpec.decode("max(52dlu;default):grow"),},
		
		new RowSpec[] {FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,FormSpecs.DEFAULT_ROWSPEC,FormSpecs.RELATED_GAP_ROWSPEC,RowSpec.decode("max(38dlu;default)"),FormSpecs.RELATED_GAP_ROWSPEC,RowSpec.decode("max(45dlu;default)"),FormSpecs.RELATED_GAP_ROWSPEC,RowSpec.decode("max(43dlu;default)"),FormSpecs.RELATED_GAP_ROWSPEC,RowSpec.decode("max(76dlu;default)"),FormSpecs.RELATED_GAP_ROWSPEC,RowSpec.decode("max(20dlu;default):grow"),}));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "2, 1, 41, 36, fill, fill");
	
		textVista = new JTextArea();
		scrollPane.setViewportView(textVista);
		
		textInput = new JTextField();
		frame.getContentPane().add(textInput, "2, 38, 39, 1");
		textInput.setColumns(0);
		jodete();
		opciones12();
		btnBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom = textInput.getText();
				
				if (estadoActual == Estados.JUEGO_CARGAR_NOMBRE_JUGADORES) {// el juego carga a los jugadores con su nombre y comienza el juego
					controlador.setJugador(nom);
					try {
						controlador.cargarNombreJugadores(nom);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}					
				}
				if (estadoActual == Estados.JUEGO_OPCION_1_2) {
					if (nom.equals("1")) {
						nombreJugador();
						estadoActual = Estados.JUEGO_CARGAR_NOMBRE_JUGADORES;
					}
					if (nom.equals("2")) {
						println(nom);
						mostrarRanking();
						opciones12();
					}					
				}				
				try {
					mostrarJuegoInterfaz();
				} catch (NumberFormatException | RemoteException e1) {
					e1.printStackTrace();
				}
				textInput.setText("");
			}

		});		
		frame.getContentPane().add(btnBoton, "42, 38");
		frame.setVisible(true);
	}
	
	public void mostrarJuegoInterfaz() throws NumberFormatException, RemoteException {
		println("");
		if (estadoActual == Estados.LISTOS_PARA_COMENZAR) {
			String comienzo = textInput.getText();
			textInput.setText("");
			if (comienzo.equals("si")) {
				comenzarJuego();
			}
		}		
		if (estadoActual == Estados.COMIENZO_JUEGO) { //inicia el juego
			if (estadoJuegoActual == EstadoJuego.CAMBIO_PALO) {
				String palo = textInput.getText();
				textInput.setText("");
				this.controlador.cartaComodin10(palo);
			}
			if (estadoJuegoActual == EstadoJuego.TIRANDO_CARTA){
				String cartaTirar = textInput.getText();
				textInput.setText("");
				this.cartaTirar = cartaTirar;
				this.controlador.seleccionarOpcion("b");	
			}
			if (estadoJuegoActual == EstadoJuego.OPCION_SELECCIONADA) {
				String opcion = textInput.getText();
				textInput.setText("");
				this.controlador.seleccionarOpcion(opcion);
			}
			if (estadoJuegoActual == EstadoJuego.MOSTRAR_OPCIONES_USUARIO) {
				this.controlador.mostrarOpcionesUsuarioJugando();				
			}
		}
		if (estadoActual == Estados.FIN_JUEGO) {
			println("ya esta");
		}
	}
	
	public void mostrarOpcionesUsuario(boolean opcionb, boolean opcionc, boolean opciond, boolean opcionf) {
		menuPrincipal();
		if (opcionb) {println("b - Tirar carta");}
		if(opcionc) {println("c - Robar una carta");}
		if (opciond) {println("d - Terminar turno");}
		println("e - Cantar jodete");
		if (opcionf) {println("f - Cambiar de palo");}
		println("g - No canto jodete el jugador anterior");
		estadoJuegoActual = EstadoJuego.OPCION_SELECCIONADA;
	}

	private EstadoJuego estadoJuegoSegunSeleccion(String opcion) {			
		switch (opcion) {
		case "b":return EstadoJuego.OPCION_B;				
		case "c":return  EstadoJuego.OPCION_C;				
		case "d":return EstadoJuego.OPCION_D;				
		case "e":return  EstadoJuego.OPCION_E;				
		case "f":return EstadoJuego.OPCION_F;
		case "g":return EstadoJuego.OPCION_G;
		}
		if(opcion.equals("")) {return EstadoJuego.OPCION_INVALIDA;}
		return null;		
	}
		
	private void comenzarJuego() throws RemoteException {
		this.controlador.comenzarJuego();		
	}
	
	public void obetnerOpcionElegida(String op) throws NumberFormatException, RemoteException {
		estadoJuegoActual = this.controlador.opcionesDeJuego(estadoJuegoSegunSeleccion(op), this.cartaTirar);
		this.cartaTirar = "";
	}
	
	public void menuPrincipal() {
		println("");
		println("Selecciona una opción:");		
	}

	public void jodete() {
		println("########################");
		println("####### JODETE #######");
		println("########################");
		println();	
		println();
	
	}
	public void nombreJugador() {
		println("");
		println("Escriba nombre de jugador");
	}
		
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void seleecionCartaTirar() throws NumberFormatException, RemoteException {
		println("");
		println("Seleccione carta a tirar");
	}
	
	public void verCartaMazoAbajo(String carta) {
	//modificar esto
	}

	public void verCartas(ArrayList<String> cartas){
    //modificar esto
	}		
	public void mostrarEsperandoJugadores() {
		println("");
		println("Esperando Jugadores...");		
	}	
	public void mostrarCartaInexistente() {
		println("");
		println("Carta Inexistente");		
	}
	public void mostrarCartaNoCoincidente() {
		println("");
		println("Carta con numero o palo no valido");		
	}	
	public void mostrarListosParaComenzar() {
		println("");
		println("Comenzar juego (S/N)?");
		estadoActual = Estados.LISTOS_PARA_COMENZAR;			
	}	
	public void mostrarRanking() {
		Ganadores lista=(Ganadores) serializador.readFirstObject();
		println("Ganadores : " + lista.getNombresGanadores());
		println("Puntos : " + lista.getCantGanadas());
	}
	public void mostrarCantidadJugadores(int cantidad) {
		println("");
		println("Los jugadores han sido agregados");
		println("");
		println("Cantidad jugadores: " + cantidad);	
	}
	public void mostrarComienzoJuego() {
		println("");
		println("El juego ha comenzado");	
		estadoActual = Estados.COMIENZO_JUEGO;
	}
	public void mostrarFinTurno() {
		println("");
		println("Fin de turno");	
	}
	public void mostrarCambioJugador(String nombre) {	
		println("");
		println("Turno del jugador " + nombre);		
	}
	public void mostrarNoSePuedeRobarCartas() {
		println("");
		println("Mazo vacio, no se pudieron robar cartas");
	}
	public void mostrarCambioRonda() {
		println("");
		println("El jugador se ha quedado sin cartas");
		println("");
		println("Una nueva ronda ha comenzado");
	}
	public void mostrarCartaTiradaCorrectamente() {
		println("");
		println("Carta tirada correctamente");					
	}
	public void mostrarCartaTirada(Integer numero, String palo) {
		println("");
		if (numero != 0) {println("Carta "+ numero + " de" + palo + " tirada");}
		else {println("Carta comodin tirada, levanta 5 cortas si cantaste jodete");
			  println("Se puede tirar cualquier carta");}
		println("");
			switch((Integer)numero) {
			
			case 2:println("El jugador levantara cartas extra");
				break;
			case 4:println("El siguiente jugador pierde su turno");
				break;
			case 7:println("Puedes tirar otra carta");	
				break;
			case 10:println("Cambio de palo, seleccione palo a cambia:");
					println("oro, basto, copa, espada");
				break;
			case 11:println("Puedes tirar otra carta");
				break;
			case 12:println("Cambio de sentido la ronda");
				break;
			}
	}
	public void mostrarJodete0() {
		println();
		println("El siguiente jugador levanta 5 cartas");
	}
	public void mostrarCambioColor(Palo palo) {
		println("");
		println("El palo a sido cambiado a: " + palo);		
		estadoJuegoActual = EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
	}
	public void mostrarCantidadJugadoresErronea() {
		println("");
		println("Cantidad de jugadores erronea");	
	}		
	public void mostrarJugadorAgregado(String nombre, Integer cantidad) {
		println("");
		println("El jugador " + nombre + " fue agregado");	
		println("Cantidad jugadores: " + cantidad);	
	}	
	public void mostrarTest(String algo) {
		println();
		println("test: " +algo);
	}	
	public void mostrarFinJuego(String ultimojugador) {
		println();
		println("El juego ha finalizado, el ganador es: " + ultimojugador );
		estadoJuegoActual = EstadoJuego.FIN_JUEGO;
		estadoActual = Estados.FIN_JUEGO;
	}	
	public void mostrarCantoJodete(Integer canto) {
		println();
		switch((Integer)canto) {
		case 1:	println("Cantaste Jodete");
			break;
		case 2: println("Jodete mal cantado, levantas 5 cartas");
			break;
		case 3: println("El jugador anteror no canto jodete, levanta 5 cartas");
			break;
		} 
	}	
	public void serializar(String ganador) {
		if (serializador!=null) {
		Ganadores lista=(Ganadores) serializador.readFirstObject();
		lista.agregarGanador(ganador);
		serializador.writeOneObject(lista);
		serializador=null;}
	}		
	private void opciones12() {
		println();
		println("Seleccione una opcion");
		println("1) Cargar jugador");
		println("2) Ver ranking");
		
	}
	public void mostrarJugadorEliminado(String jugador) {
		println();
		println("El jugador " + jugador + " fue eliminado");
	}
	public void mostrarJugadorEliminadoPropio() {
		println();
		println("Fuiste eliminado");
	}

}
