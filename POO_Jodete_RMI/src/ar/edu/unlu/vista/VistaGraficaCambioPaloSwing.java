package ar.edu.unlu.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

class VistaGraficaCambioPaloSwing extends JFrame {
    private JComboBox<String> comboBox;
    private JButton btnElegir;
    private String paloSeleccionado;

    public VistaGraficaCambioPaloSwing() throws RemoteException {
        setTitle("Selector de Palo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 300, 150);
        getContentPane().setLayout(null);

        comboBox = new JComboBox<>();
        comboBox.setBounds(10, 20, 120, 30);
        comboBox.addItem("oro");
        comboBox.addItem("basto");
        comboBox.addItem("copa");
        comboBox.addItem("espada");
        getContentPane().add(comboBox);

        btnElegir = new JButton("Elegir");
        btnElegir.setBounds(150, 20, 100, 30);
        getContentPane().add(btnElegir);

        // Acción del botón "Elegir"
        btnElegir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paloSeleccionado = (String) comboBox.getSelectedItem();
                setVisible(false); // Oculta la ventana
                dispose(); // Cierra la ventana
            }
        });
    }

    public String getPaloSeleccionado() {
        return paloSeleccionado;
    }


}