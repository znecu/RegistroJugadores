package edu.ucne.RegistroJugadores.presentation.partidas.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroJugadores.domain.partidas.model.Partida


@Composable
fun ListPartidaScreen(
    viewModel: ListPartidaViewModel = hiltViewModel()

) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ListPartidaBody(state, viewModel::onEvent)
}

@Composable
fun ListPartidaBody(
    state: ListPartidaUiState,
    onEvent: (ListPartidaUiEvent) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        if (state.isLoading){
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
        ){
            items(state.partidas){ partida ->
                PartidaCard(
                    partida = partida,
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
    onEdit: () -> Unit,
    onDelete: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("partida_card_${partida.partidaId}")
            .clickable { onEdit() }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.weight(1f)){

            }
        }
        TextButton(
            onClick = onEdit,
            modifier = Modifier.testTag("edit_button_${partida.partidaId}")
        ){
            Text("Editar")

        }
    }
}
@Preview
@Composable
private fun ListPartidaBodyPreview(){
    MaterialTheme {
        val state = ListPartidaUiState()
        ListPartidaBody(state) { }
    }
}