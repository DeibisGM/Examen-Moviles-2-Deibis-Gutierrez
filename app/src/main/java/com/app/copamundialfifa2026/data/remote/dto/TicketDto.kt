package com.app.copamundialfifa2026.data.remote.dto

import com.app.copamundialfifa2026.data.model.Ticket
import com.app.copamundialfifa2026.data.model.TicketCategory
import com.app.copamundialfifa2026.data.model.TicketPriority
import com.app.copamundialfifa2026.data.model.TicketStatus

/**
 * Transport representation of a ticket as it would travel over the wire. Enums are sent as Strings
 * to stay backend-agnostic. Mappers below convert DTO <-> domain in a single place.
 */
data class TicketDto(
    val id: String,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val provider: String,
    val category: String,
    val createdAt: String
)

fun TicketDto.toDomain(): Ticket = Ticket(
    id = id,
    title = title,
    description = description,
    priority = TicketPriority.valueOf(priority),
    status = TicketStatus.valueOf(status),
    provider = provider,
    category = TicketCategory.valueOf(category),
    createdAt = createdAt
)

fun Ticket.toDto(): TicketDto = TicketDto(
    id = id,
    title = title,
    description = description,
    priority = priority.name,
    status = status.name,
    provider = provider,
    category = category.name,
    createdAt = createdAt
)
