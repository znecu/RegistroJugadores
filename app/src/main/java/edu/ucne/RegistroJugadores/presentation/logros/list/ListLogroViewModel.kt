package edu.ucne.RegistroJugadores.presentation.logros.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroJugadores.domain.logros.usecase.DeleteLogroUseCase
import edu.ucne.RegistroJugadores.domain.logros.usecase.ObserveLogroUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListLogroViewModel @Inject constructor(
    private val observeLogroUseCase: ObserveLogroUseCase,
    private val deleteLogroUseCase: DeleteLogroUseCase
): ViewModel(){
    private val _state = MutableStateFlow(ListLogroUiState(isLoading = true))
    val state: StateFlow<ListLogroUiState> = _state.asStateFlow()
    init {
        onEvent(ListLogroUiEvent.Load)
    }
    fun onEvent(event: ListLogroUiEvent){
        when(event){
            ListLogroUiEvent.Load -> observe()
            is ListLogroUiEvent.Delete -> onDelete(event.id)
            ListLogroUiEvent.CreateNew -> _state.update { it.copy(navigationToCreate = true) }
            is ListLogroUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListLogroUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }
    private fun observe(){
        viewModelScope.launch {
            observeLogroUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, logros = list, message = null) }
            }
        }
    }
    private fun onDelete(id: Int){
        viewModelScope.launch {
            deleteLogroUseCase(id)
            onEvent(ListLogroUiEvent.ShowMessage("Eliminado"))
        }
    }
    fun onNavigationHandled(){
        _state.update { it.copy(navigationToCreate = false , navigateToEditId = null) }
    }

}