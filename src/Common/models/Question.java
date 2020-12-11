package common.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable{
	

	private static final long serialVersionUID = 1868658614781034429L;

	private Proposition reponse;
	private ArrayList<Proposition> reponses_possibles;
	private String question;
	private String theme = "default";
	
	public Question(String question, ArrayList<Proposition> rep_possibles, Proposition rep) {
		reponse = rep;
		reponses_possibles = rep_possibles;
		this.question = question;
	}
	
	public Question(String question, ArrayList<Proposition> rep_possibles, Proposition rep, String theme) {
		this(question, rep_possibles, rep);
		this.theme=theme;
	}
	
	public Proposition getReponse() {
		return reponse;
	}
	
	public String getText() {
		return question;
	}
	
	public ArrayList<Proposition> getPropositions() {
		return reponses_possibles;
	}
	
	public String getTheme() {
		return theme;
	}
	
}
