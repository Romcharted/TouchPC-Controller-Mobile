package com.example.touchpccontroller_mobile;


/**
 * Classe représentant les paramètres de l'application.
 */
public class Settings {

    private String ip;
    private int port;
    private float sensitivity;

    /**
     * Constructeur de la classe Settings.
     *
     * @param ip          Adresse IP du serveur.
     * @param port        Port de communication avec le serveur.
     * @param sensitivity Sensibilité pour les actions de l'utilisateur.
     */
    public Settings(String ip, int port, float sensitivity) {
        this.ip = ip;
        this.port = port;
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

