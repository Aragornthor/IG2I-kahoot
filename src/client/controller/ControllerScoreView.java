package client.controller;

import java.util.Map;

import client.views.ScoreView;

public class ControllerScoreView {
        //region Singleton
        private static ControllerScoreView instance;
        private ScoreView view;

        public static ControllerScoreView getInstance() {
            if(instance == null)
                instance = new ControllerScoreView();
            return instance;
        }
        //endregion

        //region Constructeurs
        private ControllerScoreView() { }
        //endregion

        /**
         * Parser et afficher les scores en fin de partie
         * @param mapScore Map des scores
         */
        public void updateWindow(Map<String,Integer> mapScore) {
            StringBuilder reponse = new StringBuilder("<html>");
            for(Map.Entry<String, Integer> entry : mapScore.entrySet())
                reponse.append(entry.getKey() + " : " + entry.getValue() + " points<br>");

            ControllerReponseView.getInstance().hideWindow();
            reponse.append("</html>");
            view = new ScoreView(reponse.toString());
        }

        /**
         * Cacher la fenêtre de réponse
         */
        public void hideWindow() {
            view.hideWindow();
        }
}
