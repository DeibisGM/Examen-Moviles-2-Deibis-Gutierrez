package com.app.copamundialfifa2026.ui.screens.createticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.copamundialfifa2026.core.UserMessages
import com.app.copamundialfifa2026.data.model.TicketCategory
import com.app.copamundialfifa2026.data.model.TicketPriority
import com.app.copamundialfifa2026.data.repository.ApiResult
import com.app.copamundialfifa2026.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CreateTicketUiState(
    val title: String = "",
    val description: String = "",
    val provider: String = "",
    val category: TicketCategory = TicketCategory.INVENTORY_SHORTAGE,
    val priority: TicketPriority = TicketPriority.MEDIUM,
    val isSubmitting: Boolean = false,
    val created: Boolean = false,
    val errorMessage: String? = null
)

class CreateTicketViewModel(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTicketUiState())
    val uiState: StateFlow<CreateTicketUiState> = _uiState.asStateFlow()

    fun onTitleChange(value: String) { _uiState.value = _uiState.value.copy(title = value, errorMessage = null) }
    fun onDescriptionChange(value: String) { _uiState.value = _uiState.value.copy(description = value, errorMessage = null) }
    fun onProviderChange(value: String) { _uiState.value = _uiState.value.copy(provider = value, errorMessage = null) }
    fun onCategoryChange(value: TicketCategory) { _uiState.value = _uiState.value.copy(category = value) }
    fun onPriorityChange(value: TicketPriority) { _uiState.value = _uiState.value.copy(priority = value) }

    fun submit() {
        val state = _uiState.value
        if (state.title.isBlank() || state.description.isBlank() || state.provider.isBlank()) {
            _uiState.value = state.copy(errorMessage = UserMessages.CreateTicket.EMPTY_FIELDS)
            return
        }
        _uiState.value = state.copy(isSubmitting = true, errorMessage = null)
        viewModelScope.launch {
            val result = ticketRepository.createTicket(
                title = state.title,
                description = state.description,
                provider = state.provider,
                category = state.category,
                priority = state.priority
            )
            when (result) {
                is ApiResult.Success ->
                    _uiState.value = _uiState.value.copy(isSubmitting = false, created = true)
                is ApiResult.Error ->
                    _uiState.value = _uiState.value.copy(isSubmitting = false, errorMessage = result.message)
            }
        }
    }

    class Factory(private val ticketRepository: TicketRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CreateTicketViewModel(ticketRepository) as T
    }
}
