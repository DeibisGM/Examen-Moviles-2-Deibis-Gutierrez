package com.app.copamundialfifa2026.data.remote.dto

/** Body of POST /tickets. */
data class CreateTicketRequest(
    val title: String,
    val description: String,
    val provider: String,
    val category: String,
    val priority: String
)

/** Body of PATCH /tickets/{id}/status. */
data class UpdateStatusRequest(
    val status: String
)

/** Body of PATCH /tickets/{id}/priority. */
data class UpdatePriorityRequest(
    val priority: String
)
