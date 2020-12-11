package common.models;

import java.io.Serializable;

public class Proposition implements Serializable {
	
	private static final long serialVersionUID = 6069094374802013749L;
	
	private String text;
	
	public Proposition(String txt) {
		text = txt;
	}
	
	public String getText() {
		return text;
	}
}
