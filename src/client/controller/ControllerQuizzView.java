package client.controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import client.models.Client;
import client.views.QuizzView;
import client.views.WaitingView;
import common.models.Question;

public class ControllerQuizzView {
    //region Singleton
    private static ControllerQuizzView instance;
    private QuizzView view;

    public static ControllerQuizzView getInstance() {
        if(instance == null)
            instance = new ControllerQuizzView();
        return instance;
    }
    //endregion

    //region Constructeurs
    private ControllerQuizzView() { }
    //endregion

    /**
     * Communique la réponse de la vue vers le Client pour envoi au serveur
     * @param reponse La réponse a envoyé
     */
    public void connectFrame(String reponse) {
        try {
            Client.getInstance().sendReponse(reponse);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "La réponse n'a pas été envoyée :\n" + e.getMessage(), "Erreur réseau", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualise la question
     * @param q La question a affiché
     */
    public void updateWindow(Question q) {
    	try {
    		ControllerReponseView.getInstance().hideWindow();
    	} catch(NullPointerException e) {
    		WaitingView.getInstance().hideWindow();
    	}
    	
        view = new QuizzView(q);
    }

    /**
     * Cacher la fenêtre courante
     */
    public void hideWindow() {
        view.hideWindow();
    }
}
