package edu.ucne.RegistroJugadores.presentation.logros.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroJugadores.domain.logros.model.Logro
import edu.ucne.RegistroJugadores.domain.logros.usecase.DeleteLogroUseCase
import edu.ucne.RegistroJugadores.domain.logros.usecase.ExisteTituloUseCase
import edu.ucne.RegistroJugadores.domain.logros.usecase.GetLogroUseCase
import edu.ucne.RegistroJugadores.domain.logros.usecase.UpsertLogroUseCase
import edu.ucne.RegistroJugadores.domain.logros.usecase.validateDescripcion
import edu.ucne.RegistroJugadores.domain.logros.usecase.validateTitulo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditLogroViewModel @Inject constructor(
    private val getLogroUseCase: GetLogroUseCase,
    private val upsertLogroUseCase: UpsertLogroUseCase,
    private val deleteLogroUseCase: DeleteLogroUseCase,
    private val existeTituloUseCase: ExisteTituloUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(value = EditLogroUiState())
    val state: StateFlow<EditLogroUiState> = _state.asStateFlow()

    fun onEvent(event: EditLogroUiEvent) {
        when (event) {
            is EditLogroUiEvent.Load -> onLoad(id = event.id)
            is EditLogroUiEvent.TituloChanged -> _state.update {
                it.copy(titulo = event.value, tituloError = null)
            }

            is EditLogroUiEvent.DescripcionChanged -> _state.update {
                it.copy(descripcion = event.value, descripcionError = null)
            }

            EditLogroUiEvent.Save -> onSave()
            EditLogroUiEvent.Delete -> onDelete()
        }

    }
    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, logroId = null) }
            return
        }
        viewModelScope.launch {
            val logro = getLogroUseCase(id)
            if (logro != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        logroId = logro.logroId,
                        titulo = logro.titulo,
                        descripcion = logro.descripcion
                    )
                }
            }

        }
    }
    private fun onSave() {
        val titulo = state.value.titulo
        val tituloValidations = validateTitulo(titulo)
        val descripcion = state.value.descripcion
        val descripcionValidations = validateDescripcion(descripcion)
        if (!tituloValidations.isValid || !descripcionValidations.isValid) {
            _state.update {
                it.copy(
                    tituloError = tituloValidations.error,
                    descripcionError = descripcionValidations.error
                )
            }
            return

        }
        viewModelScope.launch {
            val currentId = state.value.logroId
            if (existeTituloUseCase(titulo, currentId)) {
                _state.update {
                    it.copy(
                        tituloError = "Ya existe un logro con ese titulo"
                    )
                }
                return@launch
            }
            _state.update { it.copy(isSaving = true) }
            val id = state.value.logroId ?: 0
            val logro = Logro(
                logroId = id,
                titulo = titulo,
                descripcion = descripcion
            )
            val result = upsertLogroUseCase(logro)
            result.onSuccess { newId ->
                _state.value = EditLogroUiState()
            }.onFailure { e ->
                _state.update { it.copy(isSaving = false) }
            }
        }
    }
    private fun onDelete() {
        val id = state.value.logroId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteLogroUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}