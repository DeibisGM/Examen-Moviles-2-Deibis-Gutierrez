package com.app.copamundialfifa2026.data.model

/**
 * Pure ordering/filtering rules for the ticket list. Kept side-effect free so it can be unit tested
 * and reused wherever the "priority first" ordering is needed.
 */
object TicketOrdering {

    /** Highest priority first; ties broken by most recent creation date. */
    fun byPriority(tickets: List<Ticket>): List<Ticket> =
        tickets.sortedWith(
            compareByDescending<Ticket> { it.priority.weight }
                .thenByDescending { it.createdAt }
        )

    /** Optionally drops resolved/closed tickets (used by the SHOW_RESOLVED_TICKETS flag). */
    fun forList(tickets: List<Ticket>, includeResolved: Boolean): List<Ticket> {
        val visible = if (includeResolved) tickets else tickets.filter { it.isActive() }
        return byPriority(visible)
    }

    private fun Ticket.isActive(): Boolean =
        status != TicketStatus.RESOLVED && status != TicketStatus.CLOSED
}
