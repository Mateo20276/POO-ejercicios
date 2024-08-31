package Juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PruebaCartas extends JFrame {

    private JPanel cardPanel;
    private JTextField cardInput;
    private ArrayList<ImageIcon> cardImages;
    private ButtonGroup cardButtonGroup;
    private int selectedCardNumber = -1;
    private JLabel messageLabel;

    public PruebaCartas() {
        setTitle("Card Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Panel para mostrar las cartas
        cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(0, 3, 10, 10)); // 0 filas, 3 columnas, 10px de espacio

        // Scroll pane para el panel de cartas
        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Input para seleccionar el número de cartas
        JPanel inputPanel = new JPanel();
        cardInput = new JTextField(5);
        JButton showButton = new JButton("Show Cards");
        showButton.addActionListener(new ShowCardsListener());
        inputPanel.add(new JLabel("Number of cards:"));
        inputPanel.add(cardInput);
        inputPanel.add(showButton);

        // Botón para mostrar un mensaje
        JButton messageButton = new JButton("Show Message");
        messageButton.addActionListener(new ShowMessageListener());
        inputPanel.add(messageButton);

        add(inputPanel, BorderLayout.NORTH);

        // Label para mostrar el mensaje
        messageLabel = new JLabel("");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(messageLabel, BorderLayout.SOUTH);

        // Cargar imágenes de cartas
        loadCardImages();

        setVisible(true);
    }

    private void loadCardImages() {
        cardImages = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            String imagePath = "src/imagenes/" + i + " DE BASTO.png";  // Cambia a la ruta correcta
            ImageIcon originalImage = new ImageIcon(imagePath);
            Image scaledImage = originalImage.getImage().getScaledInstance(80, 130, Image.SCALE_SMOOTH); // Tamaño reducido
            cardImages.add(new ImageIcon(scaledImage));
        }
    }

    private class ShowCardsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardPanel.removeAll();
            cardButtonGroup = new ButtonGroup();
            try {
                int numCards = Integer.parseInt(cardInput.getText());
                if (numCards > 0 && numCards <= 50) {
                    for (int i = 0; i < numCards; i++) {
                        JPanel cardWithButtonPanel = new JPanel();
                        cardWithButtonPanel.setLayout(new BorderLayout());

                        JLabel cardLabel = new JLabel(cardImages.get(i));
                        cardWithButtonPanel.add(cardLabel, BorderLayout.CENTER);

                        JRadioButton cardButton = new JRadioButton();
                        int cardNumber = i + 1;
                        cardButton.addActionListener(event -> selectedCardNumber = cardNumber);
                        cardWithButtonPanel.add(cardButton, BorderLayout.SOUTH);

                        cardButtonGroup.add(cardButton);
                        cardPanel.add(cardWithButtonPanel);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 50.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
            cardPanel.revalidate();
            cardPanel.repaint();
        }
    }

    // Listener para mostrar un mensaje cuando se presione el botón
    private class ShowMessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            messageLabel.setText("Este es el texto que se muestra al presionar el botón.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PruebaCartas());
    }
}
