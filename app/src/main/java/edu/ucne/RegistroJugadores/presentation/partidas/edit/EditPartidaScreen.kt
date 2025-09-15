package edu.ucne.RegistroJugadores.presentation.partidas.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditPartidaScreen(
    viewModel: EditPartidaViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    EditPartidaBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun EditPartidaBody(
    state: EditPartidaUiState,
    onEvent: (EditPartidaUiEvent) -> Unit
){
   Column(
       modifier = Modifier
           .padding(16.dp)
   ){
       OutlinedTextField(
           value = state.jugador1ID.toString(),
           onValueChange = {onEvent(EditPartidaUiEvent.Jugador1Changed(it.toInt())) },
           label = { Text("Jugador 1: ") },
           isError = state.jugador1Error != null,
           modifier = Modifier
               .fillMaxWidth()
               .testTag("input_jugador1")
       )
       if(state.jugador1Error != null){
           Text(
               text = state.jugador1Error,
               color = MaterialTheme.colorScheme.error
           )
       }
       Spacer(Modifier.height(16.dp))
       OutlinedTextField(
           value = state.jugador2ID.toString(),
           onValueChange = {onEvent(EditPartidaUiEvent.Jugador2Changed(it.toInt())) },
           label = { Text("Jugador 2: ") },
           isError = state.jugador2Error != null,
           modifier = Modifier
               .fillMaxWidth()
               .testTag("input_jugador2")
       )
       if(state.jugador2Error != null){
           Text(
               text = state.jugador2Error,
               color = MaterialTheme.colorScheme.error
           )
       }
       Spacer(Modifier.height(16.dp))

       Button(
           onClick = { onEvent(EditPartidaUiEvent.Save)},
           enabled = !state.isSaving,
           modifier = Modifier
               .fillMaxWidth()
               .testTag("btn_guardar")
       ) {
           Text("Guardar")
       }
   }
}

@Preview
@Composable
private fun EditPartidaBodyPreview(){
    val state = EditPartidaUiState()
    MaterialTheme {
        EditPartidaBody(state = state) { }
    }
}
