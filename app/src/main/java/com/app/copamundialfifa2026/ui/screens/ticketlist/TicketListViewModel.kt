package com.app.copamundialfifa2026.ui.screens.ticketlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.app.copamundialfifa2026.core.FeatureFlags
import com.app.copamundialfifa2026.data.model.Ticket
import com.app.copamundialfifa2026.data.model.TicketOrdering
import com.app.copamundialfifa2026.data.repository.ApiResult
import com.app.copamundialfifa2026.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TicketListUiState(
    val isLoading: Boolean = true,
    val tickets: List<Ticket> = emptyList(),
    val errorMessage: String? = null
)

/**
 * Observes the repository's single source of truth and exposes the list already sorted by priority.
 *
 * Because it collects [TicketRepository.tickets] (a StateFlow), any change made elsewhere — a new
 * ticket created on another screen, or a priority change on the detail screen — is pushed here
 * automatically and re-sorted, with no manual refresh. This is the heart of the reactive,
 * event-driven behavior required by the spec.
 */
class TicketListViewModel(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TicketListUiState())
    val uiState: StateFlow<TicketListUiState> = _uiState.asStateFlow()

    init {
        // React to every emission of the shared ticket flow.
        viewModelScope.launch {
            ticketRepository.tickets.collect { tickets ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    tickets = TicketOrdering.forList(tickets, FeatureFlags.SHOW_RESOLVED_TICKETS)
                )
            }
        }
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = ticketRepository.refresh()) {
                is ApiResult.Success -> _uiState.value = _uiState.value.copy(isLoading = false)
                is ApiResult.Error -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.message
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    class Factory(private val ticketRepository: TicketRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            TicketListViewModel(ticketRepository) as T
    }
}
