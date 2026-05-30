package com.app.copamundialfifa2026

import com.app.copamundialfifa2026.data.model.TicketCategory
import com.app.copamundialfifa2026.data.model.TicketPriority
import com.app.copamundialfifa2026.data.model.TicketStatus
import com.app.copamundialfifa2026.data.repository.ApiResult
import com.app.copamundialfifa2026.data.repository.TicketRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Verifies the repository is a reactive single source of truth: mutations are reflected in the
 * shared [TicketRepository.tickets] flow. This is the backbone of the event-driven UI.
 */
class TicketRepositoryTest {

    @Test
    fun createTicketAppearsInTheFlow() = runTest {
        val repo = TicketRepository()
        val before = repo.tickets.value.size

        val result = repo.createTicket(
            title = "Missing pallets at Limón hub",
            description = "Two pallets unaccounted for",
            provider = "Transportes Limón",
            category = TicketCategory.LOGISTICS,
            priority = TicketPriority.HIGH
        )

        assertTrue(result is ApiResult.Success)
        assertEquals(before + 1, repo.tickets.value.size)
        assertNotNull(repo.tickets.value.firstOrNull { it.title == "Missing pallets at Limón hub" })
    }

    @Test
    fun updatePriorityIsReflectedInTheFlow() = runTest {
        val repo = TicketRepository()
        val target = repo.tickets.value.first()

        repo.updatePriority(target.id, TicketPriority.CRITICAL)

        val updated = repo.tickets.value.first { it.id == target.id }
        assertEquals(TicketPriority.CRITICAL, updated.priority)
    }

    @Test
    fun updateStatusIsReflectedInTheFlow() = runTest {
        val repo = TicketRepository()
        val target = repo.tickets.value.first()

        repo.updateStatus(target.id, TicketStatus.CLOSED)

        val updated = repo.tickets.value.first { it.id == target.id }
        assertEquals(TicketStatus.CLOSED, updated.status)
    }
}
