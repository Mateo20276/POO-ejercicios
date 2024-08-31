package ar.edu.unlu.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import ar.edu.unlu.baraja.Palo;
import ar.edu.unlu.controlador.Controlador;
import ar.edu.unlu.serializacion.Ganadores;
import ar.edu.unlu.serializacion.Serializador;

public class VistaGraficaRJ extends JFrame  {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField textField;
    private JTextArea textArea;
    private Controlador controlador;
    private JButton btn3;
	private static Serializador serializador=new Serializador("src/datos.dat");


    public VistaGraficaRJ() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 739, 483);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panelSuperior = new JPanel();
        contentPane.add(panelSuperior, BorderLayout.NORTH);
        
        JButton btn1 = new JButton("Ver ranking");
        panelSuperior.add(btn1);
        
        JButton btn2 = new JButton("Agregar jugador");
        panelSuperior.add(btn2);
        
        btn3 = new JButton("Comenzar juego");
        panelSuperior.add(btn3);
        btn3.setEnabled(false);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        contentPane.add(panelInferior, BorderLayout.SOUTH);
        
        textField = new JTextField();
        panelInferior.add(textField);
        textField.setColumns(20);
        
        JButton btnEnviar = new JButton("Enviar");
        panelInferior.add(btnEnviar);
        btnEnviar.setEnabled(false);
        
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarRanking();
            }
        });
        
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	nombreJugador();
            	btn1.setEnabled(false);
            	btn2.setEnabled(false);
            	btnEnviar.setEnabled(true);
            }
        });
        
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					comenzarJuego();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String texto = textField.getText();
            	try {
					agregarJugador(texto);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
            	textField.setText("");
            }
        });              
    }
    
	public void println(String texto) {
		textArea.append(texto);	
	}
	
	public void println() {
		textArea.append("");	
	}
	
	private void mostrarRanking() {
		Ganadores lista=(Ganadores) serializador.readFirstObject();
		println("Ganadores : " + lista.getNombresGanadores() + "\n");
		println("Puntos : " + lista.getCantGanadas() + "\n");		
	}
	
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;		
	}
	
	public void nombreJugador() {
		println("");
		println("Escriba nombre de jugador: \n");
	}
	
	private void agregarJugador(String jugador) throws RemoteException {
		controlador.setJugador(jugador);
		this.controlador.cargarNombreJugadores(jugador);
	}

	public void mostrarJugadorAgregado(String nombre, Integer cantidad) {
		println("");
		println("El jugador " + nombre + " fue agregado \n");	
		println("Cantidad jugadores: " + cantidad + "\n");
	}
	public void mostrarEsperandoJugadores() {
		println("");
		println("Esperando Jugadores... \n");	
		
	}
	public void mostrarListosParaComenzar() {
		println("");
		println("Si quiere comenzar el juego, presione en 'Comenzar juego' ");
		btn3.setEnabled(true);
		
		}
	private void comenzarJuego() throws RemoteException {
		this.controlador.comenzarJuego();
	}
	public void cambiarVisibilidad() {
		this.setVisible(false);
	}
		
}
