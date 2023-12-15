package ar.edu.unlu.vista;


import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import ar.edu.unlu.baraja.ICarta;
import ar.edu.unlu.baraja.Palo;
import ar.edu.unlu.controlador.Controlador;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;

public class VistaConsolaSwing implements IVista {

	private JFrame frame;
	private JTextField textInput;
	private final JButton btnBoton = new JButton("New button");
	private JTextArea textVista;
	private Controlador controlador;
	private Estados estadoActual = Estados.CARGAR_NUMERO_JUGADORES;
	private int CantidadJugadores;
	private int CantidadJugadoresActual = 0;
	private ArrayList<String> jugadores = new ArrayList<String>();
	private EstadoJuego estadoJuegoActual = EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
	private EstadoJuego estadoJuegoActualopciones;
	boolean opcionb = true;
	boolean opcionc = true;
	boolean opciond = false;
	boolean opcionf = false;

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
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(31dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(12dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(52dlu;default):grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(0dlu;default):grow"),}));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "2, 2, 41, 35, fill, fill");
		
		textVista = new JTextArea();
		scrollPane.setViewportView(textVista);
		
		textInput = new JTextField();
		frame.getContentPane().add(textInput, "2, 38, 39, 1, fill, default");
		textInput.setColumns(10);
		menuInicial();
		btnBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (estadoActual == Estados.CARGAR_NUMERO_JUGADORES) {			// comienza cargando el numero de jugadores		
					CantidadJugadores = Integer.parseInt(textInput.getText());
					if (cantidadJugadores(CantidadJugadores)) {
						estadoActual = Estados.CARGANDO_NOMBRE_JUGADORES;	
						textInput.setText("");	
					}
				}
				if (estadoActual == Estados.CARGANDO_NOMBRE_JUGADORES) {		// luego se cargan los nombres
					if((CantidadJugadoresActual == CantidadJugadores )) {
						agregarJugadores();
						estadoActual = Estados.JUEGO_CARGAR_NOMBRE_JUGADORES;	
						CantidadJugadoresActual = 0;
						textInput.setText("");	
					}
					else{						
						CantidadJugadoresActual = CantidadJugadoresActual + 1;
						println("");
						println("Escriba el nombre del jugador " + (CantidadJugadoresActual));
						agregarJugadores();
						textInput.setText("");	
						}
				}
				if (estadoActual == Estados.JUEGO_CARGAR_NOMBRE_JUGADORES) {	// el juego carga a los jugadores con su nombre y se muestran sus cartas
					for (int i = 1; i < jugadores.size(); i++) {
						controlador.cargarNombreJugadores(jugadores.get(i), CantidadJugadoresActual);
						textInput.setText("");
						CantidadJugadoresActual = CantidadJugadoresActual + 1;
					}
					estadoActual = Estados.COMIENZO_JUEGO;
				}	
				mostrarJuegoInterfaz();

			}
		});		
		frame.getContentPane().add(btnBoton, "42, 38");
		frame.setVisible(true);
	}
	
	@Override
	public void menuPrincipal() {
		println("Selecciona una opción:");		
	}
	@Override
	public void menuInicial() {
		println("########################");
		println("####### JODETE #######");
		println("########################");
		println();	
		println();
		println("Escriba la cantidad de jugadores (2 a 6)");		
	}
		
	private void agregarJugadores() {
		String nom = textInput.getText();
		this.jugadores.add(nom);
	}
	
	public void cantarJodete() {
		this.controlador.cantarJodete();
		
	}

	public boolean  robarCarta() {
		return this.controlador.robarCarta();
	}

	public boolean  cantidadJugadores(int cant) {
		return this.controlador.cantidadJugadores(cant);
		
	}

	public ICarta tirarCarta(int indice) {
		return this.controlador.tirarCarta(indice);
	}
	
	public void pasarJugador() {
		this.controlador.pasarJugador();
	}	

	public void cambioPalo(String p) {
		this.controlador.cambiarPalo(p);
	}
	
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	
	public int getCartaEspecial2() {
		return this.controlador.getCartaEspecial2();
	}
	
	public void mostrarOpcionesUsuario(boolean opcionb, boolean opcionc, boolean opciond, boolean opcionf) {
		if (opcionb) {	
			println("b - Tirar carta");
		}
		if(opcionc) {
			println("c - Robar una carta");
		}
		if (opciond) {
			println("d - Terminar turno");
		}
			println("e - Cantar jodete");
		if (opcionf) {
			println("f - Cambiar de palo");
		}
	}

	public ICarta opcionb() {
		
		String carta = textInput.getText();
		if (!(carta.equals("")) && (!carta.equals("b"))) {
			ICarta cartaax = this.tirarCarta(Integer.parseInt(carta));
			textInput.setText("");	
			estadoJuegoActualopciones = EstadoJuego.TIRANDO_CARTA;
			return cartaax;
		}
		else {
			println("Seleccione carta a tirar");
			textInput.setText("");
			return null;}
	}

	public void estadoJuegoSegunSeleccion(String opcion) {
		
		if(!opcion.equals("")) {this.estadoJuegoActual = EstadoJuego.OPCION_INVALIDA;}
			
		switch (opcion) {
		case "b":this.estadoJuegoActual = EstadoJuego.OPCION_B;
			break;
				
		case "c":this.estadoJuegoActual = EstadoJuego.OPCION_C;
			break;
				
		case "d":this.estadoJuegoActual = EstadoJuego.OPCION_D;
			break;
				
		case "e":this.estadoJuegoActual = EstadoJuego.OPCION_E;
			break;
				
		case "f":this.estadoJuegoActual = EstadoJuego.OPCION_F;
			break;
		}
		
	}
	
	public void mostrarJuegoInterfaz() {
		if (estadoActual == Estados.COMIENZO_JUEGO) { //inicia el juego
			
			if (estadoJuegoActual == EstadoJuego.MOSTRAR_OPCIONES_USUARIO) {
				menuPrincipal();
				mostrarOpcionesUsuario(opcionb, opcionc,opciond,opcionf);
				estadoJuegoActual = EstadoJuego.OPCION_SELECCIONADA;	
			}
			if (estadoJuegoActual == EstadoJuego.OPCION_SELECCIONADA) {
				String opcion = textInput.getText();
				estadoJuegoSegunSeleccion(opcion);
				
			}
			if (estadoJuegoActual != EstadoJuego.OPCION_SELECCIONADA) {
				switch((EstadoJuego) estadoJuegoActual) {
				
				case OPCION_B: opciond = true;
							   opcionb = false;
						       opcionc = false;
						       ICarta carta = this.opcionb();
						       if (estadoJuegoActualopciones == EstadoJuego.TIRANDO_CARTA) {
						    	   if(carta == null) {						    	   
							    	   opcionb = true;
							    	   opcionc = true;
							    	   opciond = false;
							       }
							       else if (carta.getNumero() == 7 || carta.getNumero()== 11) {
										opcionb = true;
										opcionc = false;
										opciond = true;
									}
									else if (carta.getNumero() == 10) {
										opcionb = false;
										opcionc = false;
										opciond = false;
										opcionf = true;
									}
						    	   estadoJuegoActualopciones = EstadoJuego.FIN;
						    	   estadoJuegoActual = EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
						    	   mostrarJuegoInterfaz();
						       }						      
					break;
					
				case OPCION_C:  estadoJuegoActual = EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
								textInput.setText("");
								opcionc = false;
								opciond = true;
								
								if (this.getCartaEspecial2() !=0) {
									opcionb=false;
								}
								if(!this.robarCarta()) {
									opcionc = true;
									opciond = false;
								}
								
								mostrarJuegoInterfaz();
									break;
					
				case OPCION_D:  estadoJuegoActual = EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
								textInput.setText("");	
								this.pasarJugador();
								opciond = false;
								opcionc = true;
								opcionb = true;							  
					break;
					
				case OPCION_E:  estadoJuegoActual = EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
								textInput.setText("");	;
								this.cantarJodete();
					break;
					
				case OPCION_INVALIDA:	estadoJuegoActual = EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
										textInput.setText("");				
					break;
				}
			}
		}
	}
	
	
	public void verCartaMazoAbajo(String cartas) {
		println("");
		println(cartas);
	}

	public void verCartas(String cartas){
		println("");
		println(cartas);
	}
	
	public void mostrarCartaInexistente() {
		println("");
		println("Carta Inexistente");		
	}

	public void mostrarCartaNoCoincidente() {
		println("");
		println("Carta con numero o palo no valido");		
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

	public void mostrarCartaNormal() {
		println("");
		println("Carta normal tirada");
	}
	
	public void mostrarCartaEspecial4() {
		println("");
		println("Carta especial 4 tirada");
		println("");
		println("El siguiente jugador pierde su turno");		
		
	}
	public void mostrarCartaEspecial7() {
		println("");
		println("Carta especial 7 tirada");
		println("");
		println("Puedes tirar otra carta");
		
	}
	
	public void mostrarCartaEspecial10() {
		println("");
		println("Carta especial 10 tirada");
		println("");
		println("Puedes tirar otra carta");
		
	}

	public void mostrarCaraEspecial11() {
		println("");
		println("Carta especial 11 tirada");
		println("");
		println("Cambio de palo");
	}

	public void mostrarCaraEspecial12() {
		println("");
		println("Carta especial 12 tirada");
		println("");
		println("Cambio de sentido la ronda");
	}

	public void mostrarCambioColor(Palo palo) {
		println("");
		println("El palo a sido cambiado a: " + palo);		
	}

	public void mostrarCantidadJugadoresErronea() {
		println("");
		println("Cantidad de jugadores erronea");	
	}

	
	
	

}
