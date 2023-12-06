package com.example.touchpccontroller_mobile;

import android.util.Log;
import android.view.KeyEvent;

import com.example.touchpccontroller_mobile.server.SendData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe utilitaire pour envoyer des données de touche au serveur.
 */
public class SendKeyData {

    /**
     * Envoie le code de touche au serveur sous forme de JSON.
     *
     * @param keyCode Le code de touche.
     * @param event   L'événement de touche.
     */
    public static void sendKeyCode(int keyCode, KeyEvent event) {
        JSONObject json = new JSONObject();
        Log.d("KeyData", "Key Code: " + event.getKeyCode());
        try {
            json.put("action", "Keyboard");

            switch (keyCode) {
                case KeyEvent.KEYCODE_DEL:
                    json.put("value", "delete");
                    break;
                case KeyEvent.KEYCODE_ENTER:
                    json.put("value", "enter");
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    json.put("value", "left");
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    json.put("value", "right");
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    json.put("value", "up");
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    json.put("value", "down");
                    break;

                default:
                    char pressedKey = (char) event.getUnicodeChar();
                    json.put("value", String.valueOf(pressedKey));
                    break;
            }

            Log.d("KeyData", "Sending JSON: " + json);
            new SendData(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
