package client.models;

/**
 * Permet d'obtenir de manière synthétique les informations pour se connecter
 */
public class Connexion {

    //region Attributs
    private String adresse;
    private int port;
    //endregion

    //region Constructeurs
    public Connexion() {
        this("127.0.0.1", 8080);
    }

    public Connexion(String adresse, int port) {
        this.adresse = adresse;
        this.port = port;
    }
    //endregion

    //region Getters
    public String getAdresse() {
        return adresse;
    }

    public int getPort() {
        return port;
    }
    //endregion
}
