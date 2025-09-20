package edu.ucne.RegistroJugadores.presentation.partidas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.RegistroJugadores.presentation.componentes.TopBarComponent
import edu.ucne.RegistroJugadores.presentation.partidas.edit.EditPartidaScreen
import edu.ucne.RegistroJugadores.presentation.partidas.edit.EditPartidaUiEvent
import edu.ucne.RegistroJugadores.presentation.partidas.edit.EditPartidaViewModel
import edu.ucne.RegistroJugadores.presentation.partidas.list.ListPartidaScreen
import edu.ucne.RegistroJugadores.presentation.partidas.list.ListPartidaViewModel
import edu.ucne.RegistroJugadores.ui.theme.RegistroJugadoresTheme

@Composable
fun PartidaScreen(
    onDrawer: () -> Unit = {},
    editViewModel: EditPartidaViewModel = hiltViewModel(),
    listViewModel: ListPartidaViewModel = hiltViewModel()
) {
    var showEdit by remember { mutableStateOf(false) }
    var partidaIdEdit by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = when {
                    !showEdit -> "Registro de Partidas"
                    partidaIdEdit == null -> "Crear Partida"
                    else -> "Editar Partida"
                },
                onDrawer
            )
        },
        floatingActionButton = {
            if (!showEdit) {
                FloatingActionButton(
                    onClick = {
                        partidaIdEdit = null
                        editViewModel.onEvent(EditPartidaUiEvent.Load(null))
                        showEdit = true
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir Partida")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            PartidaScreenBody(
                showEdit = showEdit,
                editViewModel = editViewModel,
                listViewModel = listViewModel,
                onEditPartida = { partidaId ->
                    partidaIdEdit = partidaId
                    editViewModel.onEvent(EditPartidaUiEvent.Load(partidaId))
                    showEdit = true
                },
                onCancelEdit = {
                    showEdit = false
                    partidaIdEdit = null
                    editViewModel.onEvent(EditPartidaUiEvent.Cancel)
                },
                onSaveSuccess = {
                    showEdit = false
                    partidaIdEdit = null
                }
            )
        }
    }
}

@Composable
fun PartidaScreenBody(
    showEdit: Boolean,
    editViewModel: EditPartidaViewModel = hiltViewModel(),
    listViewModel: ListPartidaViewModel = hiltViewModel(),
    onEditPartida: (Int) -> Unit = {},
    onCancelEdit: () -> Unit = {},
    onSaveSuccess: () -> Unit = {}
) {
    if (showEdit) {
        EditPartidaScreen(
            viewModel = editViewModel,
            onCancel = onCancelEdit,
            onSaveSuccess = onSaveSuccess
        )
    } else {
        ListPartidaScreen(
            viewModel = listViewModel,
            onEditPartida = onEditPartida
        )
    }
}

@Preview
@Composable
fun PartidaScreenPreview() {
    RegistroJugadoresTheme {
        PartidaScreenBody(showEdit = false)
    }
}