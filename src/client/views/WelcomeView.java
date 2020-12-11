package client.views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import client.controller.ControllerWelcomeView;
import client.models.WindowProperties;
import client.models.WindowPropertiesList;
import common.models.Window;

/**
 * View pour afficher la page de connexion
 */
public class WelcomeView extends JFrame implements Window, ActionListener {

	//region Attributs
    private JTextField fieldAdrr, fieldPort;
    private JButton btn;
    //endregion

    public WelcomeView() {
        // Propriétés par défaut de la fenêtre
        super((String) new WindowProperties().getProperties().get(WindowPropertiesList.TITLE));
        Map<WindowPropertiesList, Object> properties = new WindowProperties().getProperties();
        int width = (int) properties.get(WindowPropertiesList.DEFAULT_WIDTH);
        int height = (int) properties.get(WindowPropertiesList.DEFAULT_HEIGHT);
        super.setSize(width, height);
        super.setLayout(null);

        // Bind Fermer / Stop
        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Remplissage de la fenêtre
        JLabel title = new JLabel("Bienvenue sur " + (String) properties.get(WindowPropertiesList.TITLE));
        title.setFont(new Font("Verdana", Font.PLAIN, 32));
        title.setBounds(400, 250, 680, 35);
        super.add(title);

        JLabel subtitle = new JLabel("Veuillez renseigner l'IP et le port de la partie");
        subtitle.setFont(new Font("Verdana", Font.PLAIN, 24));
        subtitle.setBounds(380, 300, 680, 30);
        super.add(subtitle);

        JLabel askAdrr = new JLabel("Adresse");
        askAdrr.setBounds(540, 520, 60, 30);
        super.add(askAdrr);

        fieldAdrr = new JTextField("127.0.0.1");
        fieldAdrr.setBounds(600, 520, 150, 30);
        super.add(fieldAdrr);

        JLabel askPort = new JLabel("Port");
        askPort.setBounds(540, 560, 60, 30);
        super.add(askPort);

        fieldPort = new JTextField("8080");
        fieldPort.setBounds(600, 560, 150, 30);
        super.add(fieldPort);

        btn = new JButton("Se connecter");
        btn.setFont(new Font("Verdana", Font.PLAIN, 14));
        btn.setBounds(540, 600, 210, 40);
        btn.addActionListener(this);
        super.add(btn);

        // Affichage de la fenetre
        super.setResizable(false);
        showWindow();
    }

    @Override
    public void showWindow() {
        super.setVisible(true);
    }

    @Override
    public void hideWindow() {
        super.setVisible(false);
    }

    @Override
    public void closeWindow() {
        super.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    //region Event de la fenêtre
    /**
     * Gestion de l'event de click sur le bouton de validation
     * @param e L'event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn) {
            String name = (String) JOptionPane.showInputDialog(this,"Comment vous appelez-vous ?", "Entrez votre pseudo",JOptionPane.QUESTION_MESSAGE, null, null, null);

            if(!fieldPort.getText().matches("\\d+"))
                JOptionPane.showMessageDialog(this, "Le port n'existe pas !", "Erreur", JOptionPane.ERROR_MESSAGE);

            ControllerWelcomeView.getInstance().connectFrame(this, fieldAdrr.getText(), Integer.parseInt(fieldPort.getText()), name);
        }
    }
    //endregion
}
