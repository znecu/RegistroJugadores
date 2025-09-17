package edu.ucne.RegistroJugadores.presentation.jugadores

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.RegistroJugadores.presentation.componentes.TopBarComponent
import edu.ucne.RegistroJugadores.presentation.jugadores.edit.EditJugadorScreen
import edu.ucne.RegistroJugadores.presentation.jugadores.edit.EditJugadorViewModel
import edu.ucne.RegistroJugadores.presentation.jugadores.list.ListJugadorViewModel
import edu.ucne.RegistroJugadores.presentation.jugadores.list.ListScreen
import edu.ucne.RegistroJugadores.ui.theme.RegistroJugadoresTheme

@Composable
fun JugadorScreen(
    onDrawer: () -> Unit = {},
    editViewModel: EditJugadorViewModel = hiltViewModel(),
    listViewModel: ListJugadorViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Registro de Jugadores",
                onDrawer
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            JugadorScreenBody(editViewModel, listViewModel)
        }
    }
}

@Composable
fun JugadorScreenBody(
    edit: EditJugadorViewModel,
    list: ListJugadorViewModel
) {
    EditJugadorScreen(edit)
    ListScreen(list)
}

@Preview
@Composable
fun JugadoresScreenPreview() {
    RegistroJugadoresTheme {
        JugadorScreenBody(hiltViewModel(), hiltViewModel())
    }
}