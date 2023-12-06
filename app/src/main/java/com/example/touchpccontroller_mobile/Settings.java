package com.example.touchpccontroller_mobile;


/**
 * Classe représentant les paramètres de l'application.
 */
public class Settings {

    private String ip;
    private int port;
    private int screenWidth;
    private int screenHeight;
    private float sensitivity;
    private boolean useLocalIP;

    /**
     * Constructeur de la classe Settings.
     *
     * @param ip          Adresse IP du serveur.
     * @param port        Port de communication avec le serveur.
     * @param screenWidth Largeur de l'écran de l'appareil.
     * @param screenHeight Hauteur de l'écran de l'appareil.
     * @param sensitivity Sensibilité pour les actions de l'utilisateur.
     */
    public Settings(String ip, int port, int screenWidth, int screenHeight, float sensitivity) {
        this.ip = ip;
        this.port = port;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.sensitivity = sensitivity;
    }


    /**
     * Obtient l'adresse IP du serveur.
     *
     * @return Adresse IP du serveur.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Modifie l'adresse IP du serveur.
     *
     * @param ip Nouvelle adresse IP du serveur.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Obtient le port de communication avec le serveur.
     *
     * @return Port de communication avec le serveur.
     */
    public int getPort() {
        return port;
    }

    /**
     * Modifie le port de communication avec le serveur.
     *
     * @param port Nouveau port de communication avec le serveur.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Obtient la largeur de l'écran de l'appareil.
     *
     * @return Largeur de l'écran de l'appareil.
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Modifie la largeur de l'écran de l'appareil.
     *
     * @param screenWidth Nouvelle largeur de l'écran de l'appareil.
     */
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    /**
     * Obtient la hauteur de l'écran de l'appareil.
     *
     * @return Hauteur de l'écran de l'appareil.
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Modifie la hauteur de l'écran de l'appareil.
     *
     * @param screenHeight Nouvelle hauteur de l'écran de l'appareil.
     */
    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    /**
     * Obtient la sensibilité pour les actions de l'utilisateur.
     *
     * @return Sensibilité pour les actions de l'utilisateur.
     */
    public float getSensitivity() {
        return sensitivity;
    }

    /**
     * Modifie la sensibilité pour les actions de l'utilisateur.
     *
     * @param sensitivity Nouvelle sensibilité pour les actions de l'utilisateur.
     */
    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }
}

