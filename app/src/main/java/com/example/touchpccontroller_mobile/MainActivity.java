    package com.example.touchpccontroller_mobile;

    import android.os.Bundle;
    import android.util.Log;
    import android.view.KeyEvent;
    import android.view.View;
    import android.widget.Button;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.Fragment;
    import androidx.navigation.NavController;
    import androidx.navigation.Navigation;
    import androidx.navigation.ui.AppBarConfiguration;
    import androidx.navigation.ui.NavigationUI;

    import com.example.touchpccontroller_mobile.databinding.ActivityMainBinding;
    import com.example.touchpccontroller_mobile.server.SendData;
    import com.google.android.material.bottomnavigation.BottomNavigationView;

    import java.util.Objects;

    /**
     * L'activité principale de l'application qui gère la navigation entre les fragments.
     */
    public class MainActivity extends AppCompatActivity {

        private Button sendButton;
        private View touchView;

        private ActionManager actionManager;

        private ActivityMainBinding binding;

        /**
         * Méthode appelée lors de la création de l'activité.
         *
         * @param savedInstanceState L'état précédemment enregistré de l'activité.
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            BottomNavigationView navView = findViewById(R.id.nav_view);

            // Configuration de la barre d'applications pour la navigation
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_main,  R.id.navigation_media, R.id.navigation_settings)
                    .build();

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }

        /**
         * Méthode appelée lorsqu'une touche est enfoncée.
         *
         * @param keyCode Le code de la touche enfoncée.
         * @param event   L'événement de la touche enfoncée.
         * @return true si l'événement est traité, sinon false.
         */
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            int currentDestinationId = navController.getCurrentDestination().getId();

            // si dans la nav on est bien sur le fragment MainFragment
            if (currentDestinationId == R.id.navigation_main) {
                SendKeyData.sendKeyCode(keyCode, event);
                return true;
            }

            return super.onKeyDown(keyCode, event);
        }

    }
