package com.app.copamundialfifa2026.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.app.copamundialfifa2026.core.AppConstants
import com.app.copamundialfifa2026.data.model.Ticket
import com.app.copamundialfifa2026.data.model.TicketCategory
import com.app.copamundialfifa2026.data.model.TicketPriority
import com.app.copamundialfifa2026.data.model.TicketStatus
import com.app.copamundialfifa2026.data.remote.mock.MockTicketData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

/**
 * Single source of truth for tickets.
 *
 * The in-memory [MutableStateFlow] plays the role the Room `Flow` plays in a persisted app: every
 * mutation emits a new list, and every screen observing [tickets] reacts automatically. This is the
 * backbone of the event-driven UI — creating a ticket or changing its priority updates this flow,
 * and the list/detail recompose without any manual refresh.
 *
 * A network delay is simulated so the UI exercises real loading/success/error states. Swapping to a
 * live backend means replacing the bodies below with [com.app.copamundialfifa2026.data.remote.TicketApiService]
 * calls; consumers stay untouched.
 */
class TicketRepository {

    private val _tickets = MutableStateFlow(MockTicketData.seed())
    val tickets: StateFlow<List<Ticket>> = _tickets.asStateFlow()

    /** Loads the ticket list (simulated). Returns a result so the UI can show an error state. */
    suspend fun refresh(): ApiResult<Unit> {
        return try {
            delay(AppConstants.Api.MOCK_LATENCY_MS)
            ApiResult.Success(Unit)
        } catch (_: Exception) {
            ApiResult.Error(com.app.copamundialfifa2026.core.UserMessages.Errors.NETWORK)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun createTicket(
        title: String,
        description: String,
        provider: String,
        category: TicketCategory,
        priority: TicketPriority
    ): ApiResult<Ticket> {
        return try {
            delay(AppConstants.Api.MOCK_LATENCY_MS)
            val ticket = Ticket(
                id = "TCK-" + (1000 + _tickets.value.size + 1),
                title = title.trim(),
                description = description.trim(),
                priority = priority,
                status = TicketStatus.OPEN,
                provider = provider.trim(),
                category = category,
                createdAt = LocalDate.now().toString()
            )
            // Emit a new list -> every observer (the list screen) updates immediately.
            _tickets.value = _tickets.value + ticket
            ApiResult.Success(ticket)
        } catch (_: Exception) {
            ApiResult.Error(com.app.copamundialfifa2026.core.UserMessages.Errors.GENERIC)
        }
    }

    suspend fun updateStatus(id: String, status: TicketStatus): ApiResult<Unit> =
        mutate(id) { it.copy(status = status) }

    suspend fun updatePriority(id: String, priority: TicketPriority): ApiResult<Unit> =
        mutate(id) { it.copy(priority = priority) }

    /** Applies [transform] to the matching ticket and re-emits the list. */
    private suspend fun mutate(id: String, transform: (Ticket) -> Ticket): ApiResult<Unit> {
        return try {
            delay(AppConstants.Api.MOCK_LATENCY_MS)
            _tickets.value = _tickets.value.map { if (it.id == id) transform(it) else it }
            ApiResult.Success(Unit)
        } catch (_: Exception) {
            ApiResult.Error(com.app.copamundialfifa2026.core.UserMessages.Errors.GENERIC)
        }
    }
}
