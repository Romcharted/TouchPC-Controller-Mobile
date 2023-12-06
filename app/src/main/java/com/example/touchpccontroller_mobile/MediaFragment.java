package com.example.touchpccontroller_mobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.touchpccontroller_mobile.server.SendData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Fragment pour le contrôle multimédia
 */
public class MediaFragment extends Fragment {

    /**
     * Constructeur par défaut pour MediaFragment.
     * Constructeur public vide requis.
     */
    public MediaFragment() {
        // Constructeur public vide requis
    }

    /**
     * Crée une nouvelle instance de MediaFragment.
     *
     * @return Une nouvelle instance du fragment MediaFragment.
     */
    public static MediaFragment newInstance() {
        return new MediaFragment();
    }

    /**
     * Crée et retourne la vue associée au fragment.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return La vue associée au fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_media, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        ImageView volumeDownButton = view.findViewById(R.id.volumeDown);
        ImageView volumeUpButton = view.findViewById(R.id.volumeUp);
        Button moveForwardButton = view.findViewById(R.id.moveForward);
        Button goBackButton = view.findViewById(R.id.goBack);
        ImageView nextButton = view.findViewById(R.id.next);
        ImageView previousButton = view.findViewById(R.id.previous);
        ImageView playPauseButton = view.findViewById(R.id.playPause);
        ImageView btnUp = view.findViewById(R.id.btnUp);
        ImageView btnDown = view.findViewById(R.id.btnDown);

        volumeDownButton.setOnClickListener(v -> envoyerActionAuServeur("Volume Down"));
        volumeUpButton.setOnClickListener(v -> envoyerActionAuServeur("Volume Up"));
        moveForwardButton.setOnClickListener(v -> envoyerActionAuServeur("Move Forward 10s"));
        goBackButton.setOnClickListener(v -> envoyerActionAuServeur("Go back 10s"));
        nextButton.setOnClickListener(v -> envoyerActionAuServeur("Next"));
        previousButton.setOnClickListener(v -> envoyerActionAuServeur("Previous"));
        playPauseButton.setOnClickListener(v -> envoyerActionAuServeur("Play/Pause"));
        btnUp.setOnClickListener(v -> envoyerActionAuServeur("Up"));
        btnDown.setOnClickListener(v -> envoyerActionAuServeur("Down"));

        return view;
    }

    /**
     * Envoie l'action spécifiée au serveur.
     *
     * @param action L'action de contrôle multimédia à envoyer au serveur.
     */
    private void envoyerActionAuServeur(String action) {
        // Envoie l'action au serveur
        JSONObject json = new JSONObject();
        try {
            json.put("action", "MediaControl");
            json.put("value", action);
            new SendData(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
