package edu.ucne.RegistroJugadores.presentation.logros

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.RegistroJugadores.presentation.componentes.TopBarComponent
import edu.ucne.RegistroJugadores.presentation.logros.edit.EditLogroScreen
import edu.ucne.RegistroJugadores.presentation.logros.edit.EditLogroViewModel
import edu.ucne.RegistroJugadores.presentation.logros.list.ListLogroViewModel
import edu.ucne.RegistroJugadores.presentation.logros.list.ListScreen
import edu.ucne.RegistroJugadores.ui.theme.RegistroJugadoresTheme

@Composable
fun LogroScreen(
    onDrawer: () -> Unit = {},
    editViewModel: EditLogroViewModel = hiltViewModel(),
    listViewModel: ListLogroViewModel = hiltViewModel()
){
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Registro de Logros",
                onDrawer
            )
        }
    ){
        innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ){
            LogroScreenBody(editViewModel, listViewModel)
        }
    }
}
@Composable
fun LogroScreenBody(
    edit: EditLogroViewModel,
    list: ListLogroViewModel
){
    EditLogroScreen(edit)
    ListScreen(list)
}

@Preview
@Composable
fun LogrosScreenPreview(){
    RegistroJugadoresTheme {
        LogroScreenBody(hiltViewModel(), hiltViewModel())
    }
}