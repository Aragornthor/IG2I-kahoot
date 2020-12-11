package client.views;

import client.models.WindowProperties;
import client.models.WindowPropertiesList;
import common.models.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

/**
 * View salle d'attente
 */
public class WaitingView extends JFrame implements Window {
    //region Constructeurs
    public static WaitingView instance;
	
	
	public WaitingView() {
        // Propriétés par défaut de la fenêtre
        super((String) new WindowProperties().getProperties().get(WindowPropertiesList.TITLE));
        Map<WindowPropertiesList, Object> properties = new WindowProperties().getProperties();
        int width = (int) properties.get(WindowPropertiesList.DEFAULT_WIDTH);
        int height = (int) properties.get(WindowPropertiesList.DEFAULT_HEIGHT);
        super.setSize(width, height);
        super.setLayout(null);
        instance = this;
        // Bind Fermer / Stop
        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Remplissage de la fenêtre
        JLabel title = new JLabel("Vous êtes dans la salle d'attente !");
        title.setFont(new Font("Verdana", Font.PLAIN, 32));
        title.setBounds(350, 250, 680, 35);
        super.add(title);

        JLabel subtitle = new JLabel("Veuillez attendre d'autres joueurs pour lancer la partie...");
        subtitle.setFont(new Font("Verdana", Font.PLAIN, 24));
        subtitle.setBounds(290, 300, 680, 30);
        super.add(subtitle);

        // Affichage de la fenetre
        super.setResizable(false);
        showWindow();
    }
    //endregion

	public static WaitingView getInstance() {
		return instance;
	}
	
	
    @Override
    public void showWindow() { super.setVisible(true); }

    @Override
    public void hideWindow() { super.setVisible(false); }

    @Override
    public void closeWindow() { super.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); }
}
