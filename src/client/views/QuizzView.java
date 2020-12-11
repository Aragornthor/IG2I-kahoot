package client.views;

import client.controller.ControllerQuizzView;
import client.models.Client;
import client.models.WindowProperties;
import client.models.WindowPropertiesList;
import common.models.Question;
import common.models.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * View pour afficher les questions
 */
public class QuizzView extends JFrame implements Window, ActionListener {

    //region Attributs
    private JLabel question;
    private List<JRadioButton> radios;
    private JButton btn;
    //endregion

    //region Constructeurs
    public QuizzView(Question q) {
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
        question = new JLabel(q.getText());
        question.setFont(new Font("Verdana", Font.PLAIN, 24));
        question.setBounds(10, 5, 1260, 50);
        super.add(question);

        ButtonGroup radioGroup = new ButtonGroup();
        radios = new ArrayList<>();
        JRadioButton btn0 = new JRadioButton(q.getPropositions().get(0).getText());
        btn0.setBounds(10, 90, 1260, 30);
        radios.add(btn0);
        radioGroup.add(btn0);
        super.add(btn0);
        JRadioButton btn1 = new JRadioButton(q.getPropositions().get(1).getText());
        btn1.setBounds(10, 130, 1260, 30);
        radios.add(btn1);
        radioGroup.add(btn1);
        super.add(btn1);
        JRadioButton btn2 = new JRadioButton(q.getPropositions().get(2).getText());
        btn2.setBounds(10, 170, 1260, 30);
        radios.add(btn2);
        radioGroup.add(btn2);
        super.add(btn2);
        JRadioButton btn3 = new JRadioButton(q.getPropositions().get(3).getText());
        btn3.setBounds(10, 210, 1260, 30);
        radios.add(btn3);
        radioGroup.add(btn3);
        super.add(btn3);

        JProgressBar bar = new JProgressBar();
        bar.setMinimum(0);
        bar.setMaximum(5000);
        super.add(bar);
        bar.setBounds(0, 650, 1280, 20);

        Thread th = new Thread() {
            @Override
            public void run() {
                for(int i = 5000; i >= 0; --i) {
                    bar.setValue(i);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        th.start();

        btn = new JButton("Valider");
        btn.setBounds(10, 270, 150, 40);
        super.add(btn);
        btn.addActionListener(this);

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

    /**
     * Gestion de l'event de click sur le bouton de validation
     * @param e L'event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn) {
            String reponse = "";
            for(JRadioButton btnRadio : radios) {
                if (btnRadio.isSelected()) {
                    reponse = btnRadio.getText();
                }
                btnRadio.setEnabled(false);
            }
            btn.setEnabled(false);

            ControllerQuizzView.getInstance().connectFrame(reponse);
        }
    }
}
