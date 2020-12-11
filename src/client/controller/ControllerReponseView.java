package client.controller;

import client.views.ReponseView;

public class ControllerReponseView {

    //region Singleton
    private static ControllerReponseView instance;
    private ReponseView view;

    public static ControllerReponseView getInstance() {
        if(instance == null)
            instance = new ControllerReponseView();
        return instance;
    }
    //endregion

    //region Constructeurs
    private ControllerReponseView() { }
    //endregion

    /**
     * Actualiser la fenêtre de réponse
     * @param isBonneReponse Bonne ou mauvaise réponse ?
     * @param reponse La bonne réponse
     */
    public void updateWindow(boolean isBonneReponse, String reponse) {
    	ControllerQuizzView.getInstance().hideWindow();
        view = new ReponseView(isBonneReponse, reponse);
    }

    /**
     * Cacher la fenêtre de réponse
     */
    public void hideWindow() {
        view.hideWindow();
    }
}
