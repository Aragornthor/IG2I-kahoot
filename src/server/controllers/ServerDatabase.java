package server.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import common.models.Proposition;
import common.models.Question;

public class ServerDatabase {
	private Connection con;
	
	/*
	 * Création de connexion à la base de données
	 */
	public ServerDatabase(String host, String username, String password, String database) throws SQLException {
		con = DriverManager.getConnection("jdbc:mysql://"+host+"/"+database+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);
	}
	
	/*
	 * Récupère la liste des thèmes auprès de la base de données
	 * @return HashMap<Integer, String>
	 */
	public HashMap<Integer, String> getThemes() throws SQLException{
		
		HashMap<Integer, String> data = new HashMap<>();
		
		String request = "SELECT * FROM categorie";
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(request);
		while (res.next()) {
			data.put(res.getInt(1), res.getString(2));
		}
		res.close();
		stmt.close();
		return data;
	}
	
	/*
	 * Récupère la liste des questions sur un thème défini auprès de la base de données
	 * @param theme_id l'identifiant du thème
	 * @param nbquestion le nombre de questions demandées
	 */
	public ArrayList<Question> getQuestions(int theme_id, int nbquestion) throws SQLException{
		ArrayList<Question> data = new ArrayList<>();
		
		String request = "SELECT * FROM question WHERE ID_CATEGORIE = ? ORDER BY RAND() LIMIT ?";
		PreparedStatement pstmt = con.prepareStatement(request);
		pstmt.setInt(1, theme_id);
		pstmt.setInt(2, nbquestion);
		ResultSet res = pstmt.executeQuery();
		while (res.next()) {
			Proposition rep = null;
			ArrayList<Proposition> propositions = new ArrayList<>(); 
			String req2 = "SELECT * FROM reponse WHERE ID_REPONSE IN (SELECT ID_REPONSE FROM propositions WHERE ID_QUESTION = ?)";
			PreparedStatement pstmt2 = con.prepareStatement(req2);
			pstmt2.setInt(1, res.getInt(1));
			ResultSet res2 = pstmt2.executeQuery();
			while (res2.next()) {
				Proposition prop = new Proposition(res2.getString(2));
				if (res2.getInt(1) == res.getInt(2)) {
					rep = prop;
				}
				propositions.add(prop);
			}
			res2.close();
			pstmt2.close();
			
			data.add(new Question(res.getString(4), propositions, rep));
		}
		res.close();
		pstmt.close();
		
		
		
		
		return data;
	}
}
