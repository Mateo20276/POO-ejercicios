package ar.edu.unlu.vista;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;

import ar.edu.unlu.baraja.Palo;
import ar.edu.unlu.controlador.Controlador;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JScrollPane;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner;


public class VistaGraficaPrincipalSwing implements IVista {

	public JFrame frame;
	private Controlador controlador;
	private VistaGraficaRJ vistaRJ;
	private VistaGraficaCambioPaloSwing vistaPalo;
	private JTextArea textArea;
    private ArrayList<ImageIcon> cardImages;
    private JScrollPane scrollPaneCartas,scrollPaneCartaArriba;
    private String cartaTirar = "";
	private JButton btnTirarCarta, btnLevantarCarta, btnTerminarTurno, btnCantarJodete, btnNoCantoJodete, btnCambiarPalo;
	private JSpinner spinner;


	public VistaGraficaPrincipalSwing() {
		initialize();
	}
	
	public void println(String texto) {
		textArea.append(texto);	
	}
	
	public void println() {
		textArea.append("");	
	}

	private void initialize() {

        vistaRJ = new VistaGraficaRJ();
        vistaRJ.setVisible(true);

        frame = new JFrame();
		frame.setBounds(100, 100, 977, 631);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		spinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
		panel_1.add(spinner);
		
		btnTirarCarta = new JButton("Tirar carta");
		panel_1.add(btnTirarCarta);
		btnTirarCarta.setVisible(false);
		
		btnLevantarCarta = new JButton("Levantar carta");
		panel_1.add(btnLevantarCarta);
		btnLevantarCarta.setVisible(false);
		
		btnTerminarTurno = new JButton("Terminar turno");
		panel_1.add(btnTerminarTurno);
		btnTerminarTurno.setVisible(false);

		btnCantarJodete = new JButton("Cantar jodete");
		panel_1.add(btnCantarJodete);
		btnCantarJodete.setVisible(false);
		
		btnNoCantoJodete = new JButton("No canto jodete");
		panel_1.add(btnNoCantoJodete);
		btnNoCantoJodete.setVisible(false);
		
		btnCambiarPalo = new JButton("Cambiar Palo");
		panel_1.add(btnCambiarPalo);
		btnCambiarPalo.setVisible(false);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		scrollPaneCartas = new JScrollPane();
		scrollPaneCartas.setBounds(10, 223, 728, 315);
		panel_2.add(scrollPaneCartas);
		
		JScrollPane scrollPaneTexto = new JScrollPane();
		scrollPaneTexto.setBounds(10, 11, 941, 182);
		panel_2.add(scrollPaneTexto);
		
		textArea = new JTextArea();
		scrollPaneTexto.setViewportView(textArea);
		
		scrollPaneCartaArriba = new JScrollPane();
		scrollPaneCartaArriba.setBounds(752, 223, 199, 315);
		panel_2.add(scrollPaneCartaArriba);
		
		btnTirarCarta.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	cartaTirar = Integer.toString((Integer) spinner.getValue());
	        	try {
		        	obetnerOpcionElegida("b");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	        }
	    });
		
		btnLevantarCarta.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	try {
		        	obetnerOpcionElegida("c");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	        }
	    });
		
		btnTerminarTurno.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	try {
		        	obetnerOpcionElegida("d");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	        }
	    });
				
		btnCantarJodete.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	try {
		        	obetnerOpcionElegida("e");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	        }
	    });		
		
		btnNoCantoJodete.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	try {
		        	obetnerOpcionElegida("g");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	        }
	    });
		
		btnCambiarPalo.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            // Deshabilitar los componentes de la ventana principal
		            disableComponents(frame.getContentPane(), false);

		            // Crear y mostrar la ventana de cambio de palo
		            vistaPalo = new VistaGraficaCambioPaloSwing();
		            vistaPalo.setVisible(true);

		            // Usar un hilo separado para evitar bloquear el EDT
		            new Thread(new Runnable() {
		                public void run() {
		                    while (vistaPalo.isVisible()) {
		                        try {
		                            Thread.sleep(10); // Espera no bloqueante
		                        } catch (InterruptedException e1) {
		                            e1.printStackTrace();
		                        }
		                    }

		                    // Obtener el palo seleccionado y habilitar los componentes
		                    String paloSeleccionado = vistaPalo.getPaloSeleccionado();
		                    if (paloSeleccionado != null) {
		                    	try {
									cambioPalo(paloSeleccionado);
									mostrarOpcionesUsuarioJugando();
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		                    }

		                    // Rehabilitar los componentes de la ventana principal
		                    SwingUtilities.invokeLater(new Runnable() {
		                        public void run() {
		                            disableComponents(frame.getContentPane(), true);
		                            frame.setVisible(true);
		                        }
		                    });
		                }
		            }).start();
		            
		        } catch (RemoteException e1) {
		            e1.printStackTrace();
		        }
		    }
		});

		frame.setVisible(false);
		
    }
	private void loadCardImages(ArrayList<String> cartas) {
        cardImages = new ArrayList<>();
        for (int i = 0; i < cartas.size(); i++) {
        	ArrayList<String> cartaSplited = splitearCarta(cartas.get(i));
            String imagePath = "src/imagenes/" + cartaSplited.get(0) + " DE "+ cartaSplited.get(1) +".png";
            ImageIcon originalImage = new ImageIcon(imagePath);
            Image scaledImage = originalImage.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            cardImages.add(new ImageIcon(scaledImage));
        }
    }		
	
	private void cambioPalo(String palo) throws RemoteException {
		this.controlador.cartaComodin10(palo);
	}
	public void menuPrincipal() {
		println("");
		println("\nSelecciona una opción:");		
	}
	public void setControlador(Controlador controlador) {
		this.vistaRJ.setControlador(controlador);
		this.controlador = controlador;	
	}
	public void verCartaMazoAbajo(String carta) {
		JPanel panelCartaArriba = new JPanel();
		scrollPaneCartaArriba.setViewportView(panelCartaArriba);
		panelCartaArriba.removeAll();
		ArrayList<String> cartaSplited = splitearCarta(carta);
		String imagePath = "src/imagenes/" + cartaSplited.get(0) + " DE " + cartaSplited.get(1).toUpperCase() + ".png";
        ImageIcon originalImage = new ImageIcon(imagePath);
        Image scaledImage = originalImage.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        panelCartaArriba.add(new JLabel(new ImageIcon(scaledImage)));
	}	
	public void verCartas(ArrayList<String> cartas) {
	    loadCardImages(cartas);
	    JPanel panelCartas = new JPanel();
	    scrollPaneCartas.setViewportView(panelCartas);
	    panelCartas.removeAll();
	    panelCartas.setLayout(new GridLayout(0, 2));
	    for (int i = 0; i < cardImages.size(); i++) {

	        JPanel cardPanel = new JPanel();
	        cardPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

	        JLabel imageLabel = new JLabel(cardImages.get(i));
	        cardPanel.add(imageLabel);

	        JLabel textLabel = new JLabel("Número: " + (i + 1));
	        cardPanel.add(textLabel);

	        panelCartas.add(cardPanel);
	    }

	    SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
	    model.setMinimum(1);   
	    model.setMaximum(cardImages.size());
	    panelCartas.revalidate();
	    panelCartas.repaint();		
	}
	private static void disableComponents(Container container, boolean enabled) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enabled);
            if (component instanceof Container) {
                disableComponents((Container) component, enabled);
            }
        }
    }
	private void mostrarOpcionesUsuarioJugando() throws RemoteException {
		this.controlador.mostrarOpcionesUsuarioJugando();
	}
	public void mostrarCartaInexistente() {
		// TODO Auto-generated method stub		
	}
	public void mostrarCartaNoCoincidente() {
		println("");
		println("\nCarta con numero o palo no valido");			
	}
	public void mostrarCantidadJugadores(int cantidad) {
		println("");
		println("\nLos jugadores han sido agregados");
		println("");
		println("\nCantidad jugadores: " + cantidad);	
	}
	public void mostrarComienzoJuego() {	
		vistaRJ.cambiarVisibilidad();
		frame.setVisible(true);
		//disableComponents(frame.getContentPane(), false);
		println("");
		println("El juego ha comenzado");
		try {
			mostrarOpcionesUsuarioJugando();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void mostrarFinTurno() {
		println("");
		println("\nFin de turno");	
	}
	public void mostrarCambioJugador(String nombre) {	
		println("");
		println("\nTurno del jugador " + nombre);		
	}
	public void mostrarNoSePuedeRobarCartas() {
		println("");
		println("\nMazo vacio, no se pudieron robar cartas");
	}
	public void mostrarCambioRonda() {
		println("");
		println("\nEl jugador se ha quedado sin cartas");
		println("");
		println("\nUna nueva ronda ha comenzado");
	}
	public void mostrarCartaTiradaCorrectamente() {
		println("");
		println("\nCarta tirada correctamente");					
	}
	public void mostrarCambioColor(Palo palo) {
		println("");
		println("\nEl palo a sido cambiado a: " + palo);		
		//estadoJuegoActual = EstadoJuego.MOSTRAR_OPCIONES_USUARIO;
	}
	public void mostrarCantidadJugadoresErronea() {
		println("");
		println("\nCantidad de jugadores erronea");	
	}
	public void mostrarEsperandoJugadores() {
		this.vistaRJ.mostrarEsperandoJugadores();	
	}
	public void mostrarListosParaComenzar() {
		this.vistaRJ.mostrarListosParaComenzar();		
	}
	public void obetnerOpcionElegida(String op) throws NumberFormatException, RemoteException {
		EstadoJuego estadoJuego = this.controlador.opcionesDeJuego(estadoJuegoSegunSeleccion(op), this.cartaTirar);	
		if (estadoJuego == EstadoJuego.MOSTRAR_OPCIONES_USUARIO){
			mostrarOpcionesUsuarioJugando();
		}
		if (estadoJuego == EstadoJuego.CAMBIO_PALO){
			btnTirarCarta.setVisible(false);
			btnLevantarCarta.setVisible(false);
			btnTerminarTurno.setVisible(false);
			btnCambiarPalo.setVisible(false);
			btnCantarJodete.setVisible(false);
			btnNoCantoJodete.setVisible(false);
			btnCambiarPalo.setVisible(true);
		}
	}
	public void mostrarOpcionesUsuario(boolean opcionb, boolean opcionc, boolean opciond, boolean opcionf) {
		btnTirarCarta.setVisible(opcionb);
		btnLevantarCarta.setVisible(opcionc);
		btnTerminarTurno.setVisible(opciond);
		btnCambiarPalo.setVisible(opcionf);
		btnCantarJodete.setVisible(true);
		btnNoCantoJodete.setVisible(true);
	}
	public void seleecionCartaTirar() throws NumberFormatException, RemoteException {
		// TODO Auto-generated method stub		
	}
	public void mostrarJugadorAgregado(String nombre, Integer cantidad) {
		this.vistaRJ.mostrarJugadorAgregado(nombre, cantidad);		
	}
	public void nombreJugador() {
		// TODO Auto-generated method stub		
	}
	public void mostrarTest(String algo) {
		println();
		println("test: " +algo);
	}
	public void mostrarCartaTirada(Integer numero, String palo) {
		println("");
		if (numero != 0) {println("\nCarta "+ numero + " de" + palo + " tirada");}
		else {println("\nCarta comodin tirada, levanta 5 cortas si cantaste jodete");
			  println("\nSe puede tirar cualquier carta");}
		println("");
			switch((Integer)numero) {			
			case 2:println("El jugador levantara cartas extra");
				break;
			case 4:println("\nEl siguiente jugador pierde su turno");
				break;
			case 7:println("\nPuedes tirar otra carta");	
				break;
			case 10:println("\nCambio de palo, seleccione palo a cambia:");
					println("\noro, basto, copa, espada");
				break;
			case 11:println("\nPuedes tirar otra carta");
				break;
			case 12:println("\nCambio de sentido la ronda");
				break;
			}
	}
	public void mostrarCantoJodete(Integer canto) {
		println();
		switch((Integer)canto) {
		case 1:	println("\nCantaste Jodete");
			break;
		case 2: println("\nJodete mal cantado, levantas 5 cartas");
			break;
		case 3: println("\nEl jugador anteror no canto jodete, levanta 5 cartas");
			break;
		} 
	}
	public void mostrarJodete0() {
		println();
		println("\nEl siguiente jugador levanta 5 cartas");
	}
	public void serializar(String jugador) {
		// TODO Auto-generated method stub		
	}
	public void mostrarFinJuego(String ultimojugador) {
		println();
		println("\nEl juego ha finalizado, el ganador es: " + ultimojugador );
		//estadoJuegoActual = EstadoJuego.FIN_JUEGO;
		//estadoActual = Estados.FIN_JUEGO;
	}
	public void mostrarJugadorEliminado(String jugador) {
		println();
		println("\nEl jugador " + jugador + " fue eliminado");
	}
	public void mostrarJugadorEliminadoPropio() {
		println();
		println("\nFuiste eliminado");
	}
	private ArrayList<String> splitearCarta(String carta){
		String[] partes = carta.split(",");
        String numero = partes[0];
        String palo = "";
        if (partes.length == 1) {
        	println(carta + " - ");
        }
        else {
            palo = partes[1];
        }

        ArrayList<String> cartaSpliteada = new ArrayList<String>();
        cartaSpliteada.add(numero);
        cartaSpliteada.add(palo);        
        return cartaSpliteada;
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
}
