package com.example.touchpccontroller_mobile.server;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Cette classe représente la configuration du serveur.
 */
public class ServerConfig {

    /**
     * Port UDP pour la communication.
     */
    private int udpPort;

    /**
     * Adresse IP du serveur.
     */
    private String ip;

    /**
     * Instance unique de la configuration du serveur.
     */
    private static ServerConfig instance;

    /**
     * Obtient l'instance unique de la configuration du serveur (singleton pattern).
     *
     * @return L'instance de la configuration du serveur.
     */
    public static ServerConfig getInstance() {
        if (instance == null) {
            instance = new ServerConfig();
        }
        return instance;
    }

    /**
     * Constructeur par défaut de la classe ServerConfig.
     * Initialise le port UDP par défaut.
     */
    private ServerConfig() {
        this.udpPort = 12345;
        this.ip = getLocalIpAddress();
    }

    /**
     * Obtient le port UDP.
     *
     * @return Le port UDP.
     */
    public int getUdpPort() {
        return udpPort;
    }

    /**
     * Définit le port
     *
     * @param port Le port
     */
    public void setPort(int port) {
        this.udpPort = port;
    }

    /**
     * Obtient adresse IP
     *
     * @return Adresse IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * Définit l'adresse IP
     *
     * @param ip L'adresse IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Obtient l'adresse IP locale du téléphone.
     *
     * @return L'adresse IP locale.
     */
    public String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress.getAddress().length == 4) {
                        Log.d("IP", "ip = " + inetAddress.getHostAddress());
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("ServerConfig", "Error getting IP address: " + e.toString());
        }
        return null;
    }
}
