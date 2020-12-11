package client.views;

import client.models.Client;
import client.models.WindowProperties;
import client.models.WindowPropertiesList;
import common.models.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

/**
 * View pour afficher les réponses
 */
public class ReponseView  extends JFrame implements Window {
    //region Constructeurs
    public ReponseView(boolean isBonneReponse, String reponse) {
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
            	try {
					Client.getInstance().closeClient();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                System.exit(0);
            }
        });

        // Remplissage de la fenêtre
        JLabel title = new JLabel( (isBonneReponse ? "Bonne" : "Mauvaise") + " réponse");
        title.setFont(new Font("Verdana", Font.PLAIN, 32));
        title.setBounds(350, 250, 680, 35);
        super.add(title);

        JLabel subtitle = new JLabel("La réponse est : " + reponse);
        subtitle.setFont(new Font("Verdana", Font.PLAIN, 24));
        subtitle.setBounds(290, 300, 680, 30);
        super.add(subtitle);

        // Affichage de la fenetre
        super.setResizable(false);
        showWindow();
    }
    //endregion

    @Override
    public void showWindow() { super.setVisible(true); }

    @Override
    public void hideWindow() { super.setVisible(false); }

    @Override
    public void closeWindow() { super.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); }
}
