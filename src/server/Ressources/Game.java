package server.ressources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import common.models.Message;
import common.models.MessageType;
import common.models.Question;
import server.controllers.ServerRunner;

public class Game implements Runnable{
	
	ArrayList<Question> questions;
	ArrayList<Connexion> connlist;
	
	Question currentQuestion;
	
	public Game(ArrayList<Question> qlist, ArrayList<Connexion> clist) {
		questions = qlist;
		connlist = clist;
	}
	
	public void answers(Connexion c, String p) {
		if (p.equals(currentQuestion.getReponse().getText())) {
			c.addScore();
		}
	}
	
	
	public HashMap<String, Integer> scoreboard() {
		HashMap<String, Integer> sb = new HashMap<>();
		ArrayList<Integer> scorelist = new ArrayList<>();
		for (Connexion c : connlist) {
			scorelist.add(c.getScore());
		}
		
		Collections.sort(scorelist, Collections.reverseOrder());
		
		ArrayList<Connexion> score_conlist = new ArrayList<>(connlist);
		
		for (int score : scorelist) {
			for (Connexion c : score_conlist) {
				if (c.getScore() == score) {
					sb.put(c.getUsername(), c.getScore());
				}
			}
		}
		
		
		return sb;
	}
	
	@Override
	public void run() {
		for (Question q : questions) {
			currentQuestion = q;
			try {
				ServerRunner.getInstance().broadcast(new Message(MessageType.Question, q));
				Thread.sleep(10000);
				ServerRunner.getInstance().broadcast(new Message(MessageType.Reponse, q.getReponse()));
				Thread.sleep(5000);
			} catch (InterruptedException | IOException e) {
				System.err.println("[GAME THREAD] Unable to sleep or send the question to players");
				e.printStackTrace();
			}
			
		}
		try {
			ServerRunner.getInstance().broadcast(new Message(MessageType.Score, scoreboard()));
			Thread.sleep(10000);
			ServerRunner.getInstance().closeServer();
		} catch (IOException | InterruptedException e) {
			System.err.println("[GAME THREAD] Unable to display scoreboard to player");
		}
		
	}
	

}
