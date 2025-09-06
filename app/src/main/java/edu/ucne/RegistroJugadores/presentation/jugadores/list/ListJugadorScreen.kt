package edu.ucne.RegistroJugadores.presentation.jugadores.list

import android.R.attr.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador

@Composable

fun ListScreen(
    viewModel: ListJugadorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ListJugadorBody(state, viewModel::onEvent)
}

@Composable
fun ListJugadorBody(
    state: ListJugadorUiState,
    onEvent: (ListJugadorUiEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ListJugadorUiEvent.CreateNew) },
                modifier = Modifier.testTag("fab_add")
            ) {
                Text("+")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("loading")
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .testTag("jugador_list")
            ) {
                items(state.jugadores) { jugador ->
                    JugadorCard(
                        jugador = jugador,
                        onClick = { onEvent(ListJugadorUiEvent.Edit(jugador.jugadorId)) },
                        onDelete = { onEvent(ListJugadorUiEvent.Delete(jugador.jugadorId)) })
                }
            }
        }
    }
}

@Composable
fun JugadorCard(
    jugador: Jugador,
    onClick: (Jugador) -> Unit,
    onDelete: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("jugador_card_${'$'}{jugador.jugadorId}")
            .clickable { onClick(jugador) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)){
                Text(jugador.nombres, style = MaterialTheme.typography.titleMedium)
                Text("Partidas: ${jugador.partidas}")
            }
            TextButton(
                onClick = {onDelete(jugador.jugadorId)},
                modifier = Modifier.testTag("delete_button_${'$'}{jugador.jugadorId}")

            ) { Text("Eliminar") }
        }
    }
}

@Preview
@Composable
private fun ListJugadorBodyBodyPreview(){
    MaterialTheme {
        val state = ListJugadorUiState()
        ListJugadorBody(state) { }
    }
}
