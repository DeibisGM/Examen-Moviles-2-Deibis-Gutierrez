package com.app.copamundialfifa2026.data.model

/**
 * Domain model used across the UI. Independent from the transport DTO so the networking layer can
 * change without touching screens.
 */
data class Ticket(
    val id: String,
    val title: String,
    val description: String,
    val priority: TicketPriority,
    val status: TicketStatus,
    val provider: String,
    val category: TicketCategory,
    val createdAt: String // ISO date, e.g. "2026-05-28"
)
