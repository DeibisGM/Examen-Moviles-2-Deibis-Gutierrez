package com.app.copamundialfifa2026.data.model

/** Lifecycle of a support ticket. */
enum class TicketStatus(val label: String) {
    OPEN("Open"),
    IN_PROGRESS("In progress"),
    RESOLVED("Resolved"),
    CLOSED("Closed")
}
