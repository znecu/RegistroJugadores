package edu.ucne.RegistroJugadores.presentation.jugadores.list

import android.app.admin.TargetUser
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.DeleteJugadorUseCase
import edu.ucne.RegistroJugadores.domain.jugadores.usecase.ObserveJugadorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListJugadorViewModel @Inject constructor(
    private val observeJugadorUseCase: ObserveJugadorUseCase,
    private val deleteJugadorUseCase: DeleteJugadorUseCase

) : ViewModel(){
    private val _state = MutableStateFlow(ListJugadorUiState(isLoading = true))
    val state: StateFlow<ListJugadorUiState> = _state.asStateFlow()

    init {
        onEvent(ListJugadorUiEvent.Load)
    }
    fun onEvent(event: ListJugadorUiEvent){
        when(event){
            ListJugadorUiEvent.Load -> observe()
            is ListJugadorUiEvent.Delete -> onDelete(event.id)
            ListJugadorUiEvent.CreateNew -> _state.update { it.copy(navigationToCreate = true) }
            is ListJugadorUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListJugadorUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }
    private fun observe(){
        viewModelScope.launch {
            observeJugadorUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, jugadores = list, message = null) }
            }
        }
    }
    private fun onDelete(id: Int){
        viewModelScope.launch {
            deleteJugadorUseCase(id)
            onEvent(ListJugadorUiEvent.ShowMessage("Eliminado"))
        }
    }
    fun onNavigationHandled(){
        _state.update { it.copy(navigationToCreate = false , navigateToEditId = null) }
    }

}