package edu.ucne.RegistroJugadores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.RegistroJugadores.presentation.jugadores.edit.EditJugadorScreen
import edu.ucne.RegistroJugadores.presentation.jugadores.edit.EditJugadorViewModel
import edu.ucne.RegistroJugadores.presentation.jugadores.edit.EditJugadorUiEvent
import edu.ucne.RegistroJugadores.presentation.jugadores.list.ListScreen
import edu.ucne.RegistroJugadores.presentation.jugadores.list.ListJugadorViewModel
import edu.ucne.RegistroJugadores.presentation.jugadores.list.ListJugadorUiEvent
import edu.ucne.RegistroJugadores.ui.theme.RegistroJugadoresTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroJugadoresTheme {
                val editViewModel: EditJugadorViewModel = hiltViewModel()
                val listViewModel: ListJugadorViewModel = hiltViewModel()
                val listState by listViewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(listState.navigateToEditId) {
                    listState.navigateToEditId?.let { id ->
                        editViewModel.onEvent(EditJugadorUiEvent.Load(id))
                        listViewModel.onNavigationHandled()
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Registro de jugadores",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        )
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        EditJugadorScreen(viewModel = editViewModel)
                        ListScreen(viewModel = listViewModel)
                    }
                }
            }
        }
    }
}