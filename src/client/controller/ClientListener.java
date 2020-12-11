package client.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

import client.models.Client;
import common.models.Message;
import common.models.Proposition;
import common.models.Question;

public class ClientListener implements Runnable {
    //region Attributs
    private Socket socket;
    private ObjectInputStream ois;
    //endregion

    //region Constructeurs
    public ClientListener(Socket socket) {
        this.socket = socket;
    }
    //endregion

    /**
     * Réceptionner et transmettre les messages venant du serveur
     */
    @SuppressWarnings({ "incomplete-switch", "unchecked" })
	@Override
    public void run() {
    	
        Message msg = null;
        try {
        	this.ois = new ObjectInputStream(socket.getInputStream());
            while (((msg = (Message) ois.readObject()) != null)){
                switch(msg.getType()) {
                    case Question:
                    	Client.getInstance().displayQuestion((Question) msg.getObject());
                    	break;
                    case Reponse:
                    	Client.getInstance().displayReponse((Proposition) msg.getObject());
                    	break;
                    case Score:
                    	HashMap<String,Integer> sc = (HashMap<String, Integer>) msg.getObject();
                    	Client.getInstance().displayScore(sc);
                        break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (msg == null) {
        	try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
