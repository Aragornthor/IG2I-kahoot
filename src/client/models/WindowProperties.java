package client.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Liste des propriétés pour les views
 */
public class WindowProperties {
    //region Attributs
    private Map<WindowPropertiesList, Object> properties;
    //endregion

    //region Constructeurs
    public WindowProperties() {
        this.properties = new HashMap<>();

        properties.put(WindowPropertiesList.TITLE, "Kahoot - Client");
        properties.put(WindowPropertiesList.DEFAULT_WIDTH, 1280);
        properties.put(WindowPropertiesList.DEFAULT_HEIGHT, 720);
    }
    //endregion

    //region Getters

    /**
     * Retourne la liste des propriétés pour les views
     * @return Les propriétés
     */
    public Map<WindowPropertiesList, Object> getProperties() {
        return properties;
    }
    //endregion
}
