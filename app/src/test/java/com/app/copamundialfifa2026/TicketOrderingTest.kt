package com.app.copamundialfifa2026

import com.app.copamundialfifa2026.data.model.Ticket
import com.app.copamundialfifa2026.data.model.TicketCategory
import com.app.copamundialfifa2026.data.model.TicketOrdering
import com.app.copamundialfifa2026.data.model.TicketPriority
import com.app.copamundialfifa2026.data.model.TicketStatus
import org.junit.Assert.assertEquals
import org.junit.Test

/** Verifies the "highest priority first" ordering that powers the reactive list. */
class TicketOrderingTest {

    private fun ticket(
        id: String,
        priority: TicketPriority,
        createdAt: String = "2026-05-20",
        status: TicketStatus = TicketStatus.OPEN
    ) = Ticket(
        id = id,
        title = "t-$id",
        description = "d",
        priority = priority,
        status = status,
        provider = "p",
        category = TicketCategory.LOGISTICS,
        createdAt = createdAt
    )

    @Test
    fun ordersByPriorityDescending() {
        val input = listOf(
            ticket("low", TicketPriority.LOW),
            ticket("critical", TicketPriority.CRITICAL),
            ticket("medium", TicketPriority.MEDIUM),
            ticket("high", TicketPriority.HIGH)
        )

        val ordered = TicketOrdering.byPriority(input).map { it.id }

        assertEquals(listOf("critical", "high", "medium", "low"), ordered)
    }

    @Test
    fun breaksPriorityTiesByMostRecentDate() {
        val input = listOf(
            ticket("older", TicketPriority.HIGH, createdAt = "2026-05-10"),
            ticket("newer", TicketPriority.HIGH, createdAt = "2026-05-28")
        )

        val ordered = TicketOrdering.byPriority(input).map { it.id }

        assertEquals(listOf("newer", "older"), ordered)
    }

    @Test
    fun forListHidesResolvedWhenFlagOff() {
        val input = listOf(
            ticket("open", TicketPriority.HIGH, status = TicketStatus.OPEN),
            ticket("resolved", TicketPriority.CRITICAL, status = TicketStatus.RESOLVED),
            ticket("closed", TicketPriority.CRITICAL, status = TicketStatus.CLOSED)
        )

        val visible = TicketOrdering.forList(input, includeResolved = false).map { it.id }

        assertEquals(listOf("open"), visible)
    }

    @Test
    fun forListKeepsResolvedWhenFlagOn() {
        val input = listOf(
            ticket("open", TicketPriority.HIGH, status = TicketStatus.OPEN),
            ticket("resolved", TicketPriority.CRITICAL, status = TicketStatus.RESOLVED)
        )

        val visible = TicketOrdering.forList(input, includeResolved = true).map { it.id }

        // Resolved is kept and, being CRITICAL, sorts first.
        assertEquals(listOf("resolved", "open"), visible)
    }
}
