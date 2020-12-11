package database.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseSender {

    private RequeteKahoot req;

    public DatabaseSender(RequeteKahoot req) {
        this.req = req;
    }

    public boolean sendCategories() {
        Map<String,String> themes = QuestionParser.getThemes();
        List<String> themesLibelle = new ArrayList<>();
        themesLibelle.addAll(themes.keySet());

        boolean allAdded = true;
        for(String s : themesLibelle) {
            if(req.addCategorie(new Categorie(-1, s.substring(s.indexOf(' ')+1))) < 0)
                allAdded = false;
        }

        return allAdded;
    }

    public boolean sendQuestion() {
        List<Question> questionList = QuestionParser.getAllQuestions();
        boolean allAdded = true;

        for(Question q : questionList) {
            Reponse[] propositions = q.getLesPropositions();
            int[] idReponses = new int[propositions.length];
            int idBonneReponse = -1;
            for(int i = 0; i < propositions.length; ++i) {
                int idReponse = sendReponse(propositions[i]);
                if(propositions[i].equals(q.getBonneReponse()))
                    idBonneReponse = idReponse;

                if (idReponse < 0)
                    allAdded = false;
                else
                    idReponses[i] = idReponse;
            }

            int idQuestion = req.addQuestion(q);

            for(int id : idReponses)
                req.addPropositions(idQuestion, id, (id == idBonneReponse));
        }
        return allAdded;
    }

    private int sendReponse(Reponse r) {
        return req.addReponse(r);
    }
    
    public static void main(String[] args) {
    	if (args.length <= 0)
    	{
    		System.err.println("Le paramètre mot de passe est requis");
    		System.exit(0);
    	}
        RequeteKahoot req = new RequeteKahoot(args[0]);
        DatabaseSender sender = new DatabaseSender(req);

        boolean cat = sender.sendCategories();
        boolean ques = sender.sendQuestion();

        if(cat)
            System.out.println("Les catégories ont toutes été ajoutées !");

        if(ques)
            System.out.println("Les questions ont toutes été ajoutées !");

    }
}
