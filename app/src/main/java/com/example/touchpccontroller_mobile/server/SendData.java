package com.example.touchpccontroller_mobile.server;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Cette classe permet l'envoi de données via le protocole UDP.
 */
public class SendData {

    /**
     * Port du serveur pour la communication UDP.
     */
    private final int serverPort;

    /**
     * Configuration du serveur.
     */
    private ServerConfig serverConfig;

    /**
     * Constructeur de la classe SendData.
     *
     * @param json Objet JSON contenant les données à envoyer.
     */
    public SendData(JSONObject json) {
        // Initialisation de la configuration du serveur
        this.serverConfig = ServerConfig.getInstance();

        // Récupération du port UDP depuis la configuration
        this.serverPort = this.serverConfig.getUdpPort();

        // Création d'un exécuteur pour exécuter la tâche d'envoi dans un thread séparé
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // Appel de la méthode d'envoi de paquet UDP
            boolean result = sendUdpPacket(json);
        });
    }

    /**
     * Envoie un paquet UDP contenant les données spécifiées.
     *
     * @param json Objet JSON contenant les données à envoyer.
     * @return true si l'envoi réussit, false sinon.
     */
    private boolean sendUdpPacket(JSONObject json) {
        // Conversion de l'objet JSON en chaîne de caractères
        String message = json.toString();

        try {
            // Création d'une socket UDP
            DatagramSocket socket = new DatagramSocket();

            // Activation de la diffusion pour la communication avec plusieurs appareils sur le réseau
            socket.setBroadcast(true);

            // Conversion de la chaîne de caractères en tableau de bytes
            byte[] data = message.getBytes();

            // Récupération de l'adresse de diffusion depuis la configuration
            InetAddress broadcastAddress = InetAddress.getByName(this.serverConfig.getIp());

            // Création d'un paquet UDP
            DatagramPacket packet = new DatagramPacket(data, data.length, broadcastAddress, serverPort);

            // Envoi du paquet
            socket.send(packet);

            // Fermeture de la socket après l'envoi
            socket.close();

            // Indication que l'envoi a réussi
            return true;
        } catch (IOException e) {
            // Gestion des exceptions en cas d'erreur d'envoi
            e.printStackTrace();
            return false;
        }
    }
}
