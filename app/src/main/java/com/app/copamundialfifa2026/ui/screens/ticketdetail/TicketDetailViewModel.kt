package com.app.copamundialfifa2026.ui.screens.ticketdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.copamundialfifa2026.core.UserMessages
import com.app.copamundialfifa2026.data.model.Ticket
import com.app.copamundialfifa2026.data.model.TicketPriority
import com.app.copamundialfifa2026.data.model.TicketStatus
import com.app.copamundialfifa2026.data.repository.ApiResult
import com.app.copamundialfifa2026.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TicketDetailUiState(
    val ticket: Ticket? = null,
    val isUpdating: Boolean = false,
    val message: String? = null,
    val errorMessage: String? = null
)

/**
 * Observes the shared flow filtered to a single ticket id, so status/priority changes are reflected
 * here the instant the repository emits — the same event stream the list consumes.
 */
class TicketDetailViewModel(
    private val ticketId: String,
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TicketDetailUiState())
    val uiState: StateFlow<TicketDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            ticketRepository.tickets.collect { tickets ->
                _uiState.value = _uiState.value.copy(
                    ticket = tickets.firstOrNull { it.id == ticketId }
                )
            }
        }
    }

    fun updateStatus(status: TicketStatus) {
        _uiState.value = _uiState.value.copy(isUpdating = true)
        viewModelScope.launch {
            when (val result = ticketRepository.updateStatus(ticketId, status)) {
                is ApiResult.Success ->
                    _uiState.value = _uiState.value.copy(
                        isUpdating = false,
                        message = UserMessages.TicketDetail.STATUS_UPDATED
                    )
                is ApiResult.Error ->
                    _uiState.value = _uiState.value.copy(
                        isUpdating = false,
                        errorMessage = result.message
                    )
            }
        }
    }

    fun updatePriority(priority: TicketPriority) {
        _uiState.value = _uiState.value.copy(isUpdating = true)
        viewModelScope.launch {
            when (val result = ticketRepository.updatePriority(ticketId, priority)) {
                is ApiResult.Success ->
                    _uiState.value = _uiState.value.copy(
                        isUpdating = false,
                        message = UserMessages.TicketDetail.PRIORITY_UPDATED
                    )
                is ApiResult.Error ->
                    _uiState.value = _uiState.value.copy(
                        isUpdating = false,
                        errorMessage = result.message
                    )
            }
        }
    }

    fun consumeMessage() {
        _uiState.value = _uiState.value.copy(message = null, errorMessage = null)
    }

    class Factory(
        private val ticketId: String,
        private val ticketRepository: TicketRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            TicketDetailViewModel(ticketId, ticketRepository) as T
    }
}
