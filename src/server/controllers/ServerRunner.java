package server.controllers;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import common.models.Message;
import common.models.Question;
import server.ressources.Connexion;
import server.ressources.Game;

public class ServerRunner {
	private static ServerRunner instance;
	private int port = 8080;
	private ServerSocket socket;
	private ArrayList<Connexion> connexions = new ArrayList<>();
	private Game game;
	private ServerDatabase sgbd;
	private Thread clth;
	private static String dbpswd;
	private int nbmaxplayers;
	
	/*
	 * Singleton runner
	 */
	private ServerRunner() throws IOException{
		
		try {
			sgbd = new ServerDatabase("127.0.0.1","root",dbpswd,"poo2020");
		} catch (SQLException e) {
			System.err.println("Impossible de se connecter à la base de données : ");
			e.printStackTrace();
		}
		
		try {
			game = new Game(askForAQuiz(),connexions);
		} catch (SQLException e) {
			System.err.println("Unable to generate game due to SQLException : ");
			e.printStackTrace();
		}
		
		socket = new ServerSocket(port);
		
		NewConnexionListener cl = new NewConnexionListener(connexions, socket);
		clth = new Thread(cl);
		clth.start();
		
		
	}
	
	/*
	 * Demande un quizz à l'utilisateur via l'entrée standard et créé un objet Game
	 * @return ArrayList<Question> un ensemble de questions.
	 */
	public ArrayList<Question> askForAQuiz() throws SQLException{
		HashMap<Integer, String> possibilities = null;
		System.out.println("Bienvenue dans le serveur Kahoot Java.\nMerci de sélectionner un quiz pour commencer.\n\nChoisir un élement parmis la liste suivante.");
		// Récupération de la liste des quizz
			possibilities = sgbd.getThemes();
			for (Map.Entry<Integer, String> entry : possibilities.entrySet()) {
				System.out.println(entry.getKey()+" -> "+entry.getValue());
			}
		
		boolean correct = false;
		int chosen = 0;
		int nb = 0;
		int nbplayers = 1;
		while (!correct) {
			System.out.println("Votre choix : ");
			Scanner scan= new Scanner(System.in);
			try {
				chosen= Integer.parseInt(scan.nextLine());
				System.out.println("Combien de questions voulez-vous poser ?");
				nb = Integer.parseInt(scan.nextLine());
				System.out.println("Combien de joueurs maximum vont jouer ?");
				nbplayers = Integer.parseInt(scan.nextLine());
			
			} catch(NumberFormatException e) {
				System.err.println("Le nombre que vous avez rentré n'est pas correct");
			}
		    if (chosen > 0 && chosen <= possibilities.size() && nb > 0 && nb <=10 && nbplayers > 0) {
		    	correct = true;
		    }
		    scan.close();
		}
		
		System.out.println("Thème choisi : "+possibilities.get(chosen) + " pour "+nb +" questions avec "+nbplayers+" joueurs");
		nbmaxplayers = nbplayers;
				
		return sgbd.getQuestions(chosen, nb);
	}
	
	/*
	 * Défini la valeur du mot de passe bdd
	 */
	public static void defineDBPassword(String password) {
		dbpswd = password;
	}
	
	/*
	 * Envoi de la réponse donnée par le joueur au serveur
	 * @param c Connexion du joueur
	 * @param p La réponse donnée par le joueur
	 */
	public void playerAnswers(Connexion c, String p) {
		game.answers(c, p);
	}
	
	/*
	 * Démarre la partie
	 */
	public void startGame() {
		Thread game_thread = new Thread(game);
		game_thread.start();
	}
	
	/*
	 * Envoi un message à chacun des joueurs
	 * @param msg Message envoyé aux clients
	 */
	public void broadcast(Message msg) throws IOException {
		for (Connexion c : connexions) {
			c.sendMsg(msg);
		}
	}
	
	/*
	 * Retourne l'instance singleton de la classe
	 * @return ServerRunner element ServerRunner
	 */
	public static ServerRunner getInstance() {
		if (instance == null) {
			try {
				instance = new ServerRunner();
			} catch (IOException e) {
				System.err.println("Unable to start server : ");
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	/*
	 * Ferme le serveur de jeu
	 */
	public void closeServer() {
		try {
			for (Connexion c : connexions) {
				c.sendMsg(null);
			}
			clth.interrupt();
			socket.close();
		} catch (IOException e) {
			System.err.println("Unable to close socket : ");
			e.printStackTrace();
		}
	}
	
	/*
	 * Récupère le nombre maximum de joueur
	 */
	public int getNbMaxPlayers() {
		return nbmaxplayers;
	}
	
	/*
	 * Définit le port du serveur
	 * @param port le port du serveur en entier
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/*
	 * Retire un joueur de la liste des connectés
	 * @param c Connexion du joueur
	 */
	public void removeUser(Connexion c) {
		connexions.remove(c);
	}
	
}
