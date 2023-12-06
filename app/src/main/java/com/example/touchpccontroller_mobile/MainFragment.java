package com.example.touchpccontroller_mobile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.touchpccontroller_mobile.server.ServerConfig;

/**
 * Fragment principal qui gère les interactions utilisateur et affiche les commandes principales.
 */
public class MainFragment extends Fragment {
    private View touchView;
    private ActionManager actionManager;

    /**
     * Constructeur public vide requis par Fragment.
     */
    public MainFragment() {
    }

    /**
     * Crée une nouvelle instance de ce fragment
     *
     * @return Une nouvelle instance de fragment MainFragment.
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    /**
     * Méthode appelée lors de la création du fragment.
     *
     * @param savedInstanceState État précédemment enregistré du fragment.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialisation de l'ActionManager et chargement des données sauvegardées
        actionManager = ActionManager.getInstance();
        loadSavedData();
    }

    /**
     * Méthode appelée pour créer et retourner la vue associée au fragment.
     *
     * @param inflater           Le LayoutInflater qui permet d'inflater la vue.
     * @param container          Le parent auquel la vue associée au fragment est attachée.
     * @param savedInstanceState L'état précédemment enregistré du fragment.
     * @return La vue associée au fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflater le layout pour ce fragment.
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Trouver les vues ici.
        touchView = view.findViewById(R.id.touchView);
        Button leftBtnClick = view.findViewById(R.id.leftBtnClick);
        Button rightBtnClick = view.findViewById(R.id.rightBtnClick);

        Button btnOpenKeyboard = view.findViewById(R.id.btnOpenKeyboard);

        btnOpenKeyboard.setOnClickListener(v -> {
            // Ouvrir le clavier virtuel
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            touchView.setFocusable(true);
            touchView.setFocusableInTouchMode(true);
            touchView.requestFocus();
            imm.showSoftInput(touchView, InputMethodManager.SHOW_FORCED);
        });

        // Configurer les auditeurs de clic pour les boutons.
        leftBtnClick.setOnClickListener(v -> actionManager.LeftClick());

        rightBtnClick.setOnClickListener(v -> actionManager.RightClick());

        // Configurer l'auditeur de toucher pour la vue tactile.
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleTouchEvent(event);
                return true;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        View v = getView();
        if(v != null){
            touchView.requestFocus();
        }
    }

    /**
     * Charger les données sauvegardées pour initialiser l'ActionManager et la configuration du serveur.
     */
    private void loadSavedData() {
        // Charger les données sauvegardées

        Settings settings = SettingsManager.loadSettings(requireContext());

        // Appliquer les données à l'ActionManager
        actionManager.setScreenWidth(settings.getScreenWidth());
        actionManager.setScreenHeight(settings.getScreenHeight());
        actionManager.setSensitivity(settings.getSensitivity());

        // Appliquer l'adresse IP à la configuration du serveur
        ServerConfig.getInstance().setIp(settings.getIp());
        ServerConfig.getInstance().setPort(settings.getPort());
    }


    /**
     * Gérer les événements tactiles pour l'ActionManager en fonction de l'action.
     *
     * @param event L'événement tactile.
     */
    private void handleTouchEvent(MotionEvent event) {
        int action = event.getAction();
        action = (event.getPointerCount() == 2 && action == MotionEvent.ACTION_MOVE) ? MotionEvent.ACTION_SCROLL : action;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                actionManager.handleTouchDown(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                actionManager.handleTouchMove(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_SCROLL:
                actionManager.handleScroll(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_UP:
                actionManager.handleTouchUpLeftClick(event.getX(), event.getY());
                break;
        }
    }

    /**
     * Fermer le clavier virtuel s'il est ouvert.
     */
    private void closeKeyboard() {
        if (touchView != null) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(touchView.getWindowToken(), 0);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        closeKeyboard();
    }
}
