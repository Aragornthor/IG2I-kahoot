package common.models;

public interface Window {
    /**
     * Permet d'afficher la fenêtre
     */
    void showWindow();

    /**
     * Permet de cacher la fenêtre (sans la delete)
     */
    void hideWindow();

    /**
     * Permet de fermer définitivement une fenêtre
     */
    void closeWindow();
}
