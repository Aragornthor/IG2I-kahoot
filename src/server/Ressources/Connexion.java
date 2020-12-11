package server.ressources;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.models.Message;
import server.controllers.ServerRunner;

public class Connexion implements Runnable{
	private Socket player_socket;
	private ObjectOutputStream oos;
	private String username = "player";
	private int score = 0;
	
	
	public Connexion(Socket soc) {
		this.player_socket = soc;
		try {
			oos = new ObjectOutputStream(soc.getOutputStream());
		} catch (IOException e) {
			System.err.println("unable to start inputstream");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// Listener for client input
		try {
			ObjectInputStream ois = new ObjectInputStream(player_socket.getInputStream());
			Message msg = null;
			while ((msg = (Message) ois.readObject()) != null) {
				handleMsg(msg);
			}
			if (msg == null) {
				endConnexion();
			}
		} catch (IOException e) {
			System.err.println("[CONNECTION THREAD] Unable to recieve data from client :");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("[CONNECTION THREAD] User Input not understood : ");
			e.printStackTrace();
		}
	}
	
	private void endConnexion() throws IOException {
		oos.writeObject(null);
		player_socket.close();
		ServerRunner.getInstance().removeUser(this);
	}
	
	public void sendMsg(Message msg) throws IOException {
		oos.writeObject(msg);
		oos.flush();
	}
	
	public void handleMsg(Message msg) {
		switch(msg.getType()) {
		case Reponse:
			ServerRunner.getInstance().playerAnswers(this, (String) msg.getObject());
			break;
		case Username:
			username = (String) msg.getObject();
			break;
		default:
			System.err.println("[CONNECTION THREAD] Message not understood by server");
			break;
			
		}
	}
	
	public Socket getSocket() {
		return player_socket;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore() {
		score++;
	}

}
