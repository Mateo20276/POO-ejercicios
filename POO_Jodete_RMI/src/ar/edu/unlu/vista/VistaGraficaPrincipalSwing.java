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
import ar.edu.unlu.serializacion.Ganadores;
import ar.edu.unlu.serializacion.Serializador;

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
	private static Serializador serializador=new Serializador("src/datos.dat");
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
		
		btnLevantarCarta = new JButton("Levantar carta");
		panel_1.add(btnLevantarCarta);
		
		btnTerminarTurno = new JButton("Terminar turno");
		panel_1.add(btnTerminarTurno);

		btnCantarJodete = new JButton("Cantar jodete");
		panel_1.add(btnCantarJodete);
		
		btnNoCantoJodete = new JButton("No canto jodete");
		panel_1.add(btnNoCantoJodete);
		
		btnCambiarPalo = new JButton("Cambiar Palo");
		panel_1.add(btnCambiarPalo);
		setearBotones(false,false,false,false,false,false);
		
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
					obetnerOpcionElegida("f");
				} catch (NumberFormatException | RemoteException e1) {
					// TODO Auto-generated catch block
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
		println("\n");
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
		println("\n");
		println("\nCarta con numero o palo no valido");			
	}
	public void mostrarCantidadJugadores(int cantidad) {
		println("\n");
		println("\nLos jugadores han sido agregados");
		println("\n");
		println("\nCantidad jugadores: " + cantidad);	
	}
	public void mostrarPalosACambiar() {
		try {
            disableComponents(frame.getContentPane(), false);

            vistaPalo = new VistaGraficaCambioPaloSwing();
            vistaPalo.setVisible(true);

            new Thread(new Runnable() {
                public void run() {
                    while (vistaPalo.isVisible()) {
                        try {
                            Thread.sleep(10); 
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    String paloSeleccionado = vistaPalo.getPaloSeleccionado();
                    if (paloSeleccionado != null) {
                    	try {
							cambioPalo(paloSeleccionado);
							mostrarOpcionesUsuarioJugando();
						} catch (RemoteException e) {
							e.printStackTrace();
						}
                    }

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
	public void mostrarComienzoJuego() {	
		vistaRJ.cambiarVisibilidad();
		frame.setVisible(true);
		//disableComponents(frame.getContentPane(), false);
		println("\n");
		println("El juego ha comenzado");
	}
	public void bloquearBoton() {
		setearBotones(false,false,false,false,false,false);
	}
	public void mostrarFinTurno() {
		println("\n");
		println("\nFin de turno");	
	}
	public void mostrarCambioJugador(String nombre) {	
		println("\n");
		println("\nTurno del jugador " + nombre);		
	}
	public void mostrarNoSePuedeRobarCartas() {
		println("\n");
		println("\nMazo vacio, no se pudieron robar cartas");
	}
	public void mostrarCambioRonda() {
		println("\n");
		println("\nEl jugador se ha quedado sin cartas");
		println("\n");
		println("\nUna nueva ronda ha comenzado");
	}
	public void mostrarCartaTiradaCorrectamente() {
		println("\n");
		println("\nCarta tirada correctamente");					
	}
	public void mostrarCambioColor(Palo palo) {
		println("\n");
		println("\nEl palo a sido cambiado a: " + palo);		
	}
	public void mostrarCantidadJugadoresErronea() {
		println("\n");
		println("\nCantidad de jugadores erronea");	
	}
	public void mostrarEsperandoJugadores() {
		this.vistaRJ.mostrarEsperandoJugadores();	
	}
	public void mostrarListosParaComenzar() {
		this.vistaRJ.mostrarListosParaComenzar();		
	}
	public void mostrarNoCantoJodetePrimero() {
		println("\nNo existe jugador anterior");
	}
	public void obetnerOpcionElegida(String op) throws NumberFormatException, RemoteException {
		EstadoJuego estadoJuego = this.controlador.opcionesDeJuego(estadoJuegoSegunSeleccion(op), this.cartaTirar);	
		if (estadoJuego == EstadoJuego.MOSTRAR_OPCIONES_USUARIO){
			mostrarOpcionesUsuarioJugando();
		}
	}
	public void mostrarPuntosJugadores(String putos_jugador) {
		println(putos_jugador);		
	}
	public void mostrarOpcionesUsuario(boolean opcionb, boolean opcionc, boolean opciond, boolean opcionf) {
		setearBotones(opcionb, opcionc, opciond, opcionf, true, true);
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
		println("\n");
		println("test: " +algo);
	}
	private void setearBotones(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f) {
		btnTirarCarta.setVisible(a);
		btnLevantarCarta.setVisible(b);
		btnTerminarTurno.setVisible(c);
		btnCambiarPalo.setVisible(d);
		btnCantarJodete.setVisible(e);
		btnNoCantoJodete.setVisible(f);
	}
	public void mostrarCartaTirada(Integer numero, String palo, Integer extra) {
		println("\n");
		if (numero != 0) {println("\nCarta "+ numero + " de" + palo + " tirada");}
		else {println("\nCarta comodin tirada, levanta 5 cortas si cantaste jodete");
			  println("\nSe puede tirar cualquier carta");}
		println("\n");
			switch((Integer)numero) {			
			case 2:println("\nEl jugador levantara " + extra + " cartas extra");
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
		println("\n");
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
		println("\n");
		println("\nEl siguiente jugador levanta 5 cartas");
	}
	public void serializar(String ganador) {
		if (serializador!=null) {
			Ganadores lista=(Ganadores) serializador.readFirstObject();
			lista.agregarGanador(ganador);
			serializador.writeOneObject(lista);
			serializador=null;}		
	}
	public void mostrarFinJuego(String ultimojugador) {
		println("\n");
		println("\nEl juego ha finalizado, el ganador es: " + ultimojugador );
	}
	public void mostrarJugadorEliminado(String jugador) {
		println("\n");
		println("\nEl jugador " + jugador + " fue eliminado");
	}
	public void mostrarPaloEnJuego(String palo) {
		println("\n[Palo en juego : " + palo + "]");
	}
	public void mostrarJugadorEliminadoPropio() {
		println("\n");
		println("\nFuiste eliminado");
	}
	private ArrayList<String> splitearCarta(String carta){
		String[] partes = carta.split(",");
        String numero = partes[0];
        String palo = partes[1];
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

	@Override
	public void setOpcionSeleccionada() {
		// TODO Auto-generated method stub
		
	}
}
