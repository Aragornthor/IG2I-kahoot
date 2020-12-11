package server.controllers;

public class Main {
	/*
	 * Méthode main pour lancer le serveur
	 */
	public static void main(String[] args) {
		if (args.length <= 0)
			System.err.println("Pas assez de paramètres");
		ServerRunner.defineDBPassword(args[0]);
		ServerRunner.getInstance();
	}
}
