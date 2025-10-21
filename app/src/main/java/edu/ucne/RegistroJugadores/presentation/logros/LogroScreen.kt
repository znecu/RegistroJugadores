package edu.ucne.RegistroJugadores.presentation.logros

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
import edu.ucne.RegistroJugadores.presentation.logros.edit.EditLogroScreen
import edu.ucne.RegistroJugadores.presentation.logros.edit.EditLogroUiEvent
import edu.ucne.RegistroJugadores.presentation.logros.edit.EditLogroViewModel
import edu.ucne.RegistroJugadores.presentation.logros.list.ListLogroViewModel
import edu.ucne.RegistroJugadores.presentation.logros.list.ListScreen
import edu.ucne.RegistroJugadores.ui.theme.RegistroJugadoresTheme

@Composable
fun LogroScreen(
    onDrawer: () -> Unit = {},
    editViewModel: EditLogroViewModel = hiltViewModel(),
    listViewModel: ListLogroViewModel = hiltViewModel()
) {
    var showEdit by remember { mutableStateOf(false) }
    var logroIdEdit by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = when {
                    !showEdit -> "Registro de Logros"
                    logroIdEdit == null -> "Crear Logro"
                    else -> "Editar Logro"
                },
                onDrawer
            )
        },
        floatingActionButton = {
            if (!showEdit) {
                FloatingActionButton(
                    onClick = {
                        logroIdEdit = null
                        editViewModel.onEvent(EditLogroUiEvent.Load(null))
                        showEdit = true
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir Logro")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LogroScreenBody(
                showEdit = showEdit,
                editViewModel = editViewModel,
                listViewModel = listViewModel,
                onEditLogro = { logroId ->
                    logroIdEdit = logroId
                    editViewModel.onEvent(EditLogroUiEvent.Load(logroId))
                    showEdit = true
                },
                onCancelEdit = {
                    showEdit = false
                    logroIdEdit = null
                },
                onSaveSuccess = {
                    showEdit = false
                    logroIdEdit = null
                }
            )
        }
    }
}

@Composable
fun LogroScreenBody(
    showEdit: Boolean,
    editViewModel: EditLogroViewModel,
    listViewModel: ListLogroViewModel,
    onEditLogro: (Int) -> Unit = {},
    onCancelEdit: () -> Unit = {},
    onSaveSuccess: () -> Unit = {}
) {
    if (showEdit) {
        EditLogroScreen(
            viewModel = editViewModel

        )
    } else {
        ListScreen(
            viewModel = listViewModel,
            onEditLogro = onEditLogro
        )
    }
}