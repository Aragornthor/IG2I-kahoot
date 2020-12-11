package client.controller;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import client.models.Client;
import client.models.Connexion;
import client.views.WaitingView;
import client.views.WelcomeView;

public class ControllerWelcomeView {
    //region Singleton
    private static ControllerWelcomeView instance;

    /**
     * Obtention du singleton
     * @return Instance du singleton
     */
    public static ControllerWelcomeView getInstance() {
        if(instance == null)
            instance = new ControllerWelcomeView();
        return instance;
    }
    //endregion

    //region Attributs
    private Client client;
    //endregion

    //region Constructeurs
    private ControllerWelcomeView() { }
    //endregion

    /**
     * Permet la connexion au serveur à l'aide de l'IP et du PORT
     * @param fenetre Fenêtre de saisie
     * @param adresse IP à utiliser pour la connexion
     * @param port PORT à utiliser pour la connexion
     */
    public void connectFrame(WelcomeView fenetre, String adresse, int port, String username) {
        try {
            client = new Client(username, new Connexion(adresse, port));
            client.setWindow(fenetre);
            fenetre.hideWindow();

            new WaitingView();

        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(fenetre, "L'hôte n'existe pas : " + e.getMessage(), "Erreur Hôte", JOptionPane.ERROR);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(fenetre, "La connexion est impossible : " + e.getMessage(), "Erreur Connexion", JOptionPane.ERROR_MESSAGE);
        }
    }

}
