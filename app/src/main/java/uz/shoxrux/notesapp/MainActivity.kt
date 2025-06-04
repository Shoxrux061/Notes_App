package uz.shoxrux.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import uz.shoxrux.notesapp.ui.navigation.AppNavHost
import uz.shoxrux.notesapp.ui.theme.NotesAppTheme
import uz.shoxrux.presentation.screens.home.HomeScreen
import uz.shoxrux.presentation.screens.home.HomeViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesAppTheme {

                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) { paddingValues ->

                    AppNavHost(paddingValues, snackbarHostState)

                }
            }
        }
    }
}