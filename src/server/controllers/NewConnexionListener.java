package server.controllers;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import server.ressources.Connexion;

public class NewConnexionListener implements Runnable{
	private ArrayList<Connexion> connection_list;
	private ServerSocket ssoc;
	/*
	 * Thread écouteur de connexions
	 */
	public NewConnexionListener(ArrayList<Connexion> lconn, ServerSocket soc) {
		ssoc = soc;
		connection_list = lconn;
	}
	
	
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted() && !ssoc.isClosed()) {
			try {
				Connexion c = new Connexion(ssoc.accept());
				Thread th = new Thread(c);
				th.start();
				synchronized(connection_list) {
					connection_list.add(c);
					if (connection_list.size() >= ServerRunner.getInstance().getNbMaxPlayers()) {
						ServerRunner.getInstance().startGame();
					}
				}
				
			} catch (IOException e) {
				System.exit(0);
			}
		}
		
	}

}
