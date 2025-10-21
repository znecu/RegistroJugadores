package edu.ucne.RegistroJugadores.presentation.tictactoe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroJugadores.presentation.componentes.TopBarComponent

@Composable
fun TicTacToeScreen(
    onDrawer: () -> Unit = {},
    viewModel: TicTacToeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.message) {
        state.message?.let {
            kotlinx.coroutines.delay(2500)
            viewModel.onEvent(TicTacToeUiEvent.Mensaje(null))
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "TicTacToe",
                onMenuClick = onDrawer
            )
        }
    ) { innerPadding ->
        TicTacToeBody(
            state = state,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TicTacToeBody(
    state: TicTacToeUiState,
    onEvent: (TicTacToeUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = state.partidaId,
                onValueChange = { onEvent(TicTacToeUiEvent.PartidaIdChange(it)) },
                label = { Text("ID de Partida") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedButton(
                onClick = { onEvent(TicTacToeUiEvent.Refrecar) },
                enabled = state.partidaId.isNotBlank() && !state.isLoading
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refrescar",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Turno de: ${state.turnoActual}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            for (fila in 0..2) {
                Row {
                    for (columna in 0..2) {
                        OutlinedButton(
                            onClick = { onEvent(TicTacToeUiEvent.CeldaClick(fila, columna)) },
                            enabled = !state.isLoading && state.tablero[fila][columna].isEmpty(),
                            modifier = Modifier
                                .size(80.dp)
                                .padding(2.dp),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = state.tablero[fila][columna],
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = when (state.tablero[fila][columna]) {
                                    "X" -> MaterialTheme.colorScheme.primary
                                    "O" -> MaterialTheme.colorScheme.secondary
                                    else -> MaterialTheme.colorScheme.onSurface
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        }

        state.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }

        state.message?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun TicTacToePreview() {
    MaterialTheme {
        TicTacToeBody(
            state = TicTacToeUiState(
                partidaId = "4",
                tablero = listOf(
                    listOf("0", "", "O"),
                    listOf("X", "0", ""),
                    listOf("X", "", "")
                ),
                turnoActual = "O",
                isLoading = false
            ),
            onEvent = {}
        )
    }
}