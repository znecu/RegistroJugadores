package edu.ucne.RegistroJugadores.presentation.partidas.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroJugadores.domain.jugadores.model.Jugador


@Composable
fun EditPartidaScreen(
    viewModel: EditPartidaViewModel = hiltViewModel(),
    onCancel: () -> Unit = {},
    onSaveSuccess: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved) {
        if (state.saved) {
            onSaveSuccess()
        }
    }

    EditPartidaBody(
        state = state,
        onEvent = { event ->
            when (event) {
                EditPartidaUiEvent.Cancel -> onCancel()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditPartidaBody(
    state: EditPartidaUiState,
    onEvent: (EditPartidaUiEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        if (state.jugadoresLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Fecha: ${state.fecha}",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        var jugador1Expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = jugador1Expanded,
            onExpandedChange = { jugador1Expanded = it }
        ) {
            OutlinedTextField(
                value = state.listaJugadores.find { it.jugadorId == state.jugador1ID }?.nombres
                    ?: "Seleccione al Jugador 1",
                onValueChange = {},
                readOnly = true,
                label = { Text("Jugador 1") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = jugador1Expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .testTag("input_jugador1")
            )

            ExposedDropdownMenu(
                expanded = jugador1Expanded,
                onDismissRequest = { jugador1Expanded = false }
            ) {
                state.listaJugadores.forEach { jugador ->
                    DropdownMenuItem(
                        text = { Text(jugador.nombres) },
                        onClick = {
                            onEvent(EditPartidaUiEvent.Jugador1Changed(jugador.jugadorId))
                            jugador1Expanded = false
                        }
                    )
                }
            }
        }

        if (state.jugador1Error != null) {
            Text(
                text = state.jugador1Error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        var jugador2Expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = jugador2Expanded,
            onExpandedChange = { jugador2Expanded = it }
        ) {
            OutlinedTextField(
                value = state.listaJugadores.find { it.jugadorId == state.jugador2ID }?.nombres
                    ?: "Selecciona al Jugador 2",
                onValueChange = {},
                readOnly = true,
                label = { Text("Jugador 2") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = jugador2Expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .testTag("input_jugador2")
            )

            ExposedDropdownMenu(
                expanded = jugador2Expanded,
                onDismissRequest = { jugador2Expanded = false }
            ) {
                state.listaJugadores.forEach { jugador ->
                    DropdownMenuItem(
                        text = { Text(jugador.nombres) },
                        onClick = {
                            onEvent(EditPartidaUiEvent.Jugador2Changed(jugador.jugadorId))
                            jugador2Expanded = false
                        }
                    )
                }
            }
        }

        if (state.jugador2Error != null) {
            Text(
                text = state.jugador2Error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val ganadorSeleccionable = state.jugador1ID != null && state.jugador2ID != null
        val ganadorID = state.ganadorID
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Selecciona al ganador:",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onEvent(EditPartidaUiEvent.GanadorChanged(state.jugador1ID!!)) },
                    enabled = ganadorSeleccionable,
                    modifier = Modifier
                        .weight(1f),
                    colors = if (ganadorID == state.jugador1ID)
                        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    else
                        ButtonDefaults.buttonColors()
                ) {
                    Text(
                        text = state.listaJugadores.find { it.jugadorId == state.jugador1ID }?.nombres
                            ?: "Jugador 1",
                        color = if (ganadorID == state.jugador1ID) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    )
                }

                Button(
                    onClick = { onEvent(EditPartidaUiEvent.GanadorChanged(state.jugador2ID!!)) },
                    enabled = ganadorSeleccionable,
                    modifier = Modifier
                        .weight(1f),
                    colors = if (ganadorID == state.jugador2ID)
                        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    else
                        ButtonDefaults.buttonColors()
                ) {
                    Text(
                        text = state.listaJugadores.find { it.jugadorId == state.jugador2ID }?.nombres
                            ?: "Jugador 2",
                        color = if (ganadorID == state.jugador2ID) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ganador: ${state.ganadorID?.let { id -> state.listaJugadores.find { it.jugadorId == id }?.nombres } ?: "Seleccione un ganador"}",
            style = MaterialTheme.typography.bodyLarge
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.esFinalizada,
                onCheckedChange = { checked ->
                    onEvent(
                        EditPartidaUiEvent.EsFinalizadaChanged(
                            checked
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "¿Terminó la partida?")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { onEvent(EditPartidaUiEvent.Save) },
                enabled = !state.jugadoresLoading && state.jugador1ID != 0 && state.jugador2ID != 0 && state.ganadorID != null,
                modifier = Modifier.testTag("btn_guardar")
            ) {
                Text("Guardar partida")
            }
            OutlinedButton(
                onClick = { onEvent(EditPartidaUiEvent.Cancel) },
                enabled = !state.jugadoresLoading
            ) {
                Text("Cancelar")
            }
        }
    }
}

@Preview
@Composable
fun EditPartidaBodyPreview() {
    val state = EditPartidaUiState(
        listaJugadores = listOf(
            Jugador(1, "Alma Maria", 4),
            Jugador(2, "Lucia Marta", 1)
        )
    )
    MaterialTheme {
        EditPartidaBody(state = state) { }
    }
}