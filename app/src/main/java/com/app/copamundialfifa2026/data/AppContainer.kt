package com.app.copamundialfifa2026.data

import com.app.copamundialfifa2026.data.repository.AuthRepository
import com.app.copamundialfifa2026.data.repository.TicketRepository

/**
 * Manual dependency injection container. Repositories are process-wide singletons so the single
 * in-memory source of truth ([TicketRepository.tickets]) is shared across every screen — that is
 * what makes the reactive updates propagate app-wide. Kept lightweight on purpose (no Hilt/Koin)
 * to match the PoC scope.
 */
object AppContainer {
    val ticketRepository: TicketRepository by lazy { TicketRepository() }
    val authRepository: AuthRepository by lazy { AuthRepository() }
}
