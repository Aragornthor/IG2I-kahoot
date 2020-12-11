package client.views;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import client.models.Client;
import client.models.WindowProperties;
import client.models.WindowPropertiesList;
import common.models.Window;

public class ScoreView extends JFrame implements Window {
	
    //region Constructeurs
    public ScoreView(String reponse) {
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
        JLabel title = new JLabel("Classement : ");
        title.setFont(new Font("Verdana", Font.PLAIN, 32));
        title.setBounds(250, 100, 680, 35);
        super.add(title);

        JLabel subtitle = new JLabel(reponse);
        subtitle.setFont(new Font("Verdana", Font.PLAIN, 24));
        subtitle.setBounds(290, 150, 680, (reponse.length() - reponse.replace("<br>", "").length())*26);
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
