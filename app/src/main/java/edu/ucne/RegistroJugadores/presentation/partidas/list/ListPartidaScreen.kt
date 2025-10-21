package edu.ucne.RegistroJugadores.presentation.partidas.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroJugadores.domain.partidas.model.Partida

@Composable
fun ListPartidaScreen(
    viewModel: ListPartidaViewModel = hiltViewModel(),
    onEditPartida: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListPartidaBody(
        state = state,
        onEvent = { event ->
            when (event) {
                is ListPartidaUiEvent.Edit -> onEditPartida(event.id)
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
fun ListPartidaBody(
    state: ListPartidaUiState,
    onEvent: (ListPartidaUiEvent) -> Unit
) {
    Box(
        modifier = Modifier
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
                .testTag("partida_list")
        ) {
            items(state.partidas) { partida ->
                PartidaCard(
                    partida = partida,
                    jugador1Nombre = state.jugadores.find { it.jugadorId == partida.jugador1Id }?.nombres ?: "Jugador ${partida.jugador1Id}",
                    jugador2Nombre = state.jugadores.find { it.jugadorId == partida.jugador2Id }?.nombres ?: "Jugador ${partida.jugador2Id}",
                    ganadorNombre = state.jugadores.find { it.jugadorId == partida.ganadorId }?.nombres ?: "Sin ganador",
                    onEdit = { onEvent(ListPartidaUiEvent.Edit(partida.partidaId)) },
                    onDelete = { onEvent(ListPartidaUiEvent.Delete(partida.partidaId)) }
                )
            }
        }
    }
}

@Composable
fun PartidaCard(
    partida: Partida,
    jugador1Nombre: String,
    jugador2Nombre: String,
    ganadorNombre: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("PartidaCard_${partida.partidaId}")
            .clickable { onEdit() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Partida #${partida.partidaId}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Fecha: ${partida.fecha}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "$jugador1Nombre vs $jugador2Nombre",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Ganador: $ganadorNombre",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = if (partida.esFinalizada) "Partida Finalizada" else "En Progreso",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (partida.esFinalizada) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                    )
                }

                Column {
                    TextButton(
                        onClick = onEdit,
                        modifier = Modifier.testTag("Editbutton_${partida.partidaId}")
                    ) {
                        Text("Editar")
                    }
                    TextButton(
                        onClick = onDelete,
                        modifier = Modifier.testTag("delete_button_${partida.partidaId}")
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewListPartidaScreen() {
    val state = ListPartidaUiState(
        partidas = listOf(
            Partida(1, "2024-01-15", 1, 2, 1, true),
            Partida(2, "2024-01-16", 3, 4, null, false)
        )
    )
    MaterialTheme {
        ListPartidaBody(state = state, onEvent = {})
    }
}