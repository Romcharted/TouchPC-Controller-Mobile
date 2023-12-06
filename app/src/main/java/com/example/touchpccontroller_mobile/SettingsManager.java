package com.example.touchpccontroller_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Classe de gestion des préférences utilisateur.
 */
public class SettingsManager {

    private static final String PREFERENCES_NAME = "AppPreferences";

    /**
     * Enregistre les paramètres dans les préférences partagées.
     *
     * @param context  Contexte de l'application.
     * @param settings Paramètres à enregistrer.
     */
    public static void saveSettings(Context context, Settings settings) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ip", settings.getIp());
        editor.putInt("port", settings.getPort());
        editor.putInt("screenWidth", settings.getScreenWidth());
        editor.putInt("screenHeight", settings.getScreenHeight());
        editor.putFloat("sensitivity", settings.getSensitivity());
        editor.apply();
    }

    /**
     * Charge les paramètres depuis les préférences partagées.
     *
     * @param context      Contexte de l'application.
     * @return Paramètres chargés depuis les préférences partagées.
     */
    public static Settings loadSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        String ip = preferences.getString("ip", "255.255.255.255");
        int port = preferences.getInt("port", 12345);
        int screenWidth = preferences.getInt("screenWidth", 1920);
        int screenHeight = preferences.getInt("screenHeight", 1080);
        float sensitivity = preferences.getFloat("sensitivity", 10.0f);

        return new Settings(ip, port, screenWidth, screenHeight, sensitivity);
    }


    /**
     * Supprime les paramètres enregistrés dans les préférences partagées.
     *
     * @param context Contexte de l'application.
     */
    public static void clearSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
