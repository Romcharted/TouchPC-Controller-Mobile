package com.example.touchpccontroller_mobile;

import android.util.Log;

import com.example.touchpccontroller_mobile.server.SendData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Cette classe gère les actions liées aux interactions utilisateur sur l'écran tactile.
 */
public class ActionManager {

    // Taille de l'écran
    private int screenWidth = 1920;
    private int screenHeight = 1080;

    // Sensibilité
    private float sensitivity = 1f;

    // Clic
    private long lastClickTime = 0;
    private float lastClickX = 0;
    private float lastClickY = 0;

    // Coordonnées
    private float startX, startY;
    private int currentX, currentY;

    // Défilement
    private boolean isScrolling = false;
    private float scrollStartX, scrollStartY;

    // Mouvement
    private boolean isMoving = false;

    private static final long DOUBLE_CLICK_THRESHOLD = 300;
    private static final float CLICK_DISTANCE_THRESHOLD = 30;

    /**
     * Obtient la largeur de l'écran.
     *
     * @return La largeur de l'écran.
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Définit la largeur de l'écran.
     *
     * @param screenWidth La nouvelle largeur de l'écran.
     */
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    /**
     * Obtient la hauteur de l'écran.
     *
     * @return La hauteur de l'écran.
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Définit la hauteur de l'écran.
     *
     * @param screenHeight La nouvelle hauteur de l'écran.
     */
    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    /**
     * Obtient la sensibilité des mouvements.
     *
     * @return La sensibilité des mouvements.
     */
    public float getSensitivity() {
        return sensitivity;
    }

    /**
     * Définit la sensibilité des mouvements.
     *
     * @param sensitivity La nouvelle sensibilité des mouvements.
     */
    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity / 10;
    }

    private static ActionManager instance;

    /**
     * Obtient l'instance unique de la classe ActionManager.
     *
     * @return L'instance de la classe ActionManager.
     */
    public static ActionManager getInstance() {
        if (instance == null) {
            instance = new ActionManager();
        }
        return instance;
    }

    /**
     * Constructeur par défaut de la classe ActionManager.
     */
    private ActionManager() {
        // Initialisation des membres si nécessaire.
    }

    /**
     * Gère l'événement du toucher sur l'écran.
     *
     * @param xPos La position en X du toucher.
     * @param yPos La position en Y du toucher.
     */
    public void handleTouchDown(float xPos, float yPos) {
        this.startX = xPos;
        this.startY = yPos;
        this.isScrolling = false;
        this.isMoving = false;
        this.currentX = 0; // Réinitialiser les coordonnées actuelles à 0
        this.currentY = 0;
    }

    /**
     * Gère le mouvement du doigt sur l'écran.
     *
     * @param xPos La nouvelle position en X du doigt.
     * @param yPos La nouvelle position en Y du doigt.
     */
    public void handleTouchMove(float xPos, float yPos) {
        // Si on est en train de faire défiler, on ne se déplace pas
        if (this.isScrolling) return;

        this.isMoving = true;

        float deltaX = xPos - this.startX;
        float deltaY = yPos - this.startY;

        // Applique la sensibilité au déplacement.
        //deltaX *= (sensitivity / 10);
        //deltaY *= (sensitivity / 10);
        Log.d("MOVE", "xPos = " +  xPos);
        Log.d("MOVE", "startX = " +  startX);
        Log.d("MOVE", "deltaX = " +  deltaX);

        // Met à jour les coordonnées de départ pour le prochain déplacement.
        this.startX = xPos;
        this.startY = yPos;

        // Met à jour les coordonnées actuelles.
        this.currentX += deltaX;
        this.currentY += deltaY;

        this.currentX *= (sensitivity/10);
        this.currentY *= (sensitivity/10);

        // Crée un objet JSON pour les coordonnées et envoie les données.
        JSONObject json = new JSONObject();
        JSONObject coords = new JSONObject();

        try {
            coords.put("x", this.currentX);
            coords.put("y", this.currentY);

            Log.d("MOVE", "x = " +  currentX);
            Log.d("MOVE", "y = " +  currentY);


            Log.d("MOVE", "y ============================= ");

            json.put("action", "MouseMove");
            json.put("coords", coords);

            new SendData(json);
            this.isScrolling = false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gère l'événement de défilement sur l'écran.
     *
     * @param xPos La position en X du défilement.
     * @param yPos La position en Y du défilement.
     */
    public void handleScroll(float xPos, float yPos) {
        if (!this.isScrolling) {
            this.isScrolling = true;
            this.scrollStartX = xPos;
            this.scrollStartY = yPos;
        } else {
            float deltaXForScroll = xPos - this.scrollStartX;
            float deltaYForScroll = yPos - this.scrollStartY;

            boolean isHorizontal = Math.abs(deltaXForScroll) > Math.abs(deltaYForScroll);

            String scrollDirection = "";
            if (isHorizontal) {
                scrollDirection = deltaXForScroll > 0 ? "right" : "left";
            } else {
                scrollDirection = deltaYForScroll > 0 ? "down" : "up";
            }

            JSONObject json = new JSONObject();
            try {
                json.put("action", "Scroll");
                json.put("scroll", scrollDirection);
                new SendData(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gère l'événement de relâchement du toucher avec un clic gauche.
     *
     * @param xPos La position en X du relâchement.
     * @param yPos La position en Y du relâchement.
     */
    public void handleTouchUpLeftClick(float xPos, float yPos) {
        long clickTime = System.currentTimeMillis();
        float clickX = xPos;
        float clickY = yPos;
        long timeSinceLastClick = clickTime - lastClickTime;

        if (timeSinceLastClick < DOUBLE_CLICK_THRESHOLD &&
                Math.abs(clickX - lastClickX) < CLICK_DISTANCE_THRESHOLD &&
                Math.abs(clickY - lastClickY) < CLICK_DISTANCE_THRESHOLD) {
            // Clic gauche double
            JSONObject json = new JSONObject();
            try {
                json.put("action", "DoubleLeftClick");
                new SendData(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (!this.isMoving) {
            LeftClick();
        }

        // Sauvegarde les coordonnées et le temps du dernier clic
        this.lastClickX = clickX;
        this.lastClickY = clickY;
        this.lastClickTime = clickTime;

        // Réinitialise la variable isScrolling
        this.isScrolling = false;
    }

    /**
     * Gère l'événement de clic droit.
     */
    public void RightClick() {
        JSONObject json = new JSONObject();
        try {
            json.put("action", "RightClick");
            Log.d("Click", "Clic droit");
            new SendData(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gère l'événement de clic gauche.
     */
    public void LeftClick() {
        JSONObject json = new JSONObject();
        try {
            json.put("action", "LeftClick");
            new SendData(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
