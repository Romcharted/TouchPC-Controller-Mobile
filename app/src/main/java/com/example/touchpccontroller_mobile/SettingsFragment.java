package com.example.touchpccontroller_mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.touchpccontroller_mobile.server.ServerConfig;

/**
 * Fragment pour les paramètres de l'application, permettant à l'utilisateur de configurer l'adresse IP, la résolution de l'écran et la sensibilité.
 */
public class SettingsFragment extends Fragment {
    private ActionManager actionManager;
    private EditText inputTextIP, inputPort, inputScreenWidth, inputScreenHeight, inputSensitivity;

    /**
     * Constructeur par défaut pour SettingsFragment.
     */
    public SettingsFragment() {
        // Constructeur public vide requis
    }

    /**
     * Crée une nouvelle instance de SettingsFragment.
     *
     * @return Une nouvelle instance du fragment SettingsFragment.
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    /**
     * Méthode appelée pour créer et retourner la vue associée au fragment.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return La vue associée au fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        inputTextIP = view.findViewById(R.id.inputTextIP);
        inputPort = view.findViewById(R.id.inputPort);
        inputScreenWidth = view.findViewById(R.id.inputScreenWidth);
        inputScreenHeight = view.findViewById(R.id.InputScreenHeight);
        inputSensitivity = view.findViewById(R.id.inputSensitivity);
        Button btnSave = view.findViewById(R.id.btnSave);

        // Initialisation de l'ActionManager
        actionManager = ActionManager.getInstance();

        // Charge les données sauvegardées dans les champs d'entrée
        Settings settings = SettingsManager.loadSettings(requireContext());
        inputTextIP.setText(settings.getIp());
        inputPort.setText(String.valueOf(settings.getPort()));
        inputScreenWidth.setText(String.valueOf(settings.getScreenWidth()));
        inputScreenHeight.setText(String.valueOf(settings.getScreenHeight()));
        inputSensitivity.setText(String.valueOf(settings.getSensitivity()));

        // Filtre d'entrée pour l'adresse IP
        inputTextIP.setFilters(new InputFilter[]{getIpFilter()});


        btnSave.setOnClickListener(v -> {
            saveSettings();
            closeKeyboard();
        });

        return view;
    }

    /**
     * Enregistre les paramètres configurés par l'utilisateur.
     */
    private void saveSettings() {
        // Enregistre les données dans les préférences partagées
        Settings settings = new Settings(
                inputTextIP.getText().toString(),
                Integer.parseInt(String.valueOf(inputPort.getText())),
                Integer.parseInt(String.valueOf(inputScreenWidth.getText())),
                Integer.parseInt(String.valueOf(inputScreenHeight.getText())),
                Float.parseFloat(String.valueOf(inputSensitivity.getText()))
        );
        SettingsManager.saveSettings(requireContext(), settings);

        // Met à jour l'ActionManager et la configuration du serveur
        updateActionManagerAndServerConfig();

        // Affiche un message de confirmation
        Toast.makeText(requireContext(), R.string.saved_settings, Toast.LENGTH_SHORT).show();
    }

    /**
     * Met à jour l'ActionManager et la configuration du serveur avec les nouvelles valeurs configurées.
     */
    private void updateActionManagerAndServerConfig() {
        // Met à jour l'ActionManager avec les nouvelles valeurs configurées
        actionManager.setScreenWidth(Integer.parseInt(String.valueOf(inputScreenWidth.getText())));
        actionManager.setScreenHeight(Integer.parseInt(String.valueOf(inputScreenHeight.getText())));
        actionManager.setSensitivity(Float.parseFloat(String.valueOf(inputSensitivity.getText())));

        // Met à jour la configuration du serveur avec la nouvelle adresse IP configurée
        ServerConfig.getInstance().setIp(String.valueOf(inputTextIP.getText()));
        ServerConfig.getInstance().setPort(Integer.parseInt(String.valueOf(inputPort.getText())));
    }

    /**
     * Obtient un filtre d'entrée pour l'adresse IP, n'acceptant que les caractères numériques et les points.
     *
     * @return Le filtre d'entrée pour l'adresse IP.
     */
    private InputFilter getIpFilter() {
        return (source, start, end, dest, dstart, dend) -> {
            String input = source.toString();
            if (input.matches("[0-9.]+")) {
                return source;
            } else {
                return "";
            }
        };
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Bouton OK pressé, rien à faire ici
                })
                .show();
    }

    /**
     * Fermer le clavier virtuel s'il est ouvert.
     */
    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getView() != null) {
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        closeKeyboard();
    }
}
