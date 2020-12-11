package client.models;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Random;

import client.controller.ClientListener;
import client.controller.ControllerQuizzView;
import client.controller.ControllerReponseView;
import client.controller.ControllerScoreView;
import client.views.WelcomeView;
import common.models.Message;
import common.models.MessageType;
import common.models.Proposition;
import common.models.Question;

public class Client {
    //region Attributs réseau
    private Socket socket;
    private ObjectOutputStream oos;
    //endregion

    //region Attributs
    private WelcomeView wv;
    private String name;
    private static Client instance;
    private String lastReponse = "";
    //endregion

    //region Constructeurs
    public Client() throws IOException {
        this("default" + new Random().nextInt(), new Connexion());
    }

    public Client(String name) throws IOException {
        this(name, new Connexion());
    }

    public Client(Connexion con) throws IOException {
        this("default" + new Random().nextInt(), con);
    }

    public Client(String name, Connexion con) throws IOException {
        this.name = name;
        this.socket = new Socket(con.getAdresse(), con.getPort());
        oos = new ObjectOutputStream(socket.getOutputStream());
        sendUsername();
        ClientListener cl = new ClientListener(socket);
        Thread th = new Thread(cl);
        th.start();
        this.instance = this;
    }
    //endregion
    
    //region Getters
    public Socket getSocket() {
        return socket;
    }

    public void setWindow(WelcomeView win) {
    	wv = win;
    }
    
    
    public String getName() {
        return name;
    }
    //endregion

    /**
     * Obtention d'une instance singleton
     * @return Instance singleton
     */
    public static Client getInstance() {
        return instance;
    }

    /**
     * Ferme le socket et retourne une instance vide à envoyer au client
     * @throws IOException
     */
    public void closeClient() throws IOException {
    	if (!socket.isClosed()) {
            oos.writeObject(null);
            socket.close();	
    	}
    }

    /**
     * Transmet la question à afficher
     * @param q La question à afficher
     */
    public void displayQuestion(Question q) {
        ControllerQuizzView.getInstance().updateWindow(q);
    }

    /**
     * Transmet la réponse à afficher
     * @param reponse La réponse à afficher
     */
    public void displayReponse(Proposition reponse) {
        boolean isBonneReponse = false;
        if(reponse.getText().equals(lastReponse))
            isBonneReponse = true;
        ControllerReponseView.getInstance().updateWindow(isBonneReponse, reponse.getText());
    }

    /**
     * Transmet les scores à afficher
     * @param mapScore La liste des scores
     */
    public void displayScore(Map<String,Integer> mapScore) {
        ControllerScoreView.getInstance().updateWindow(mapScore);
    }

    /**
     * Envoyer la réponse du client vers le serveur
     * @param reponse La réponse à envoyer
     * @throws IOException
     */
    public void sendReponse(String reponse) throws IOException {
        oos.writeObject(new Message(MessageType.Reponse, reponse));
        oos.flush();
        lastReponse = reponse;
    }
    
    /**
     *  Transmet le pseudo au serveur
     *  @throws IOException
     */
    private void sendUsername() throws IOException {
    	oos.writeObject(new Message(MessageType.Username, name));
    	oos.flush();
    }
}
