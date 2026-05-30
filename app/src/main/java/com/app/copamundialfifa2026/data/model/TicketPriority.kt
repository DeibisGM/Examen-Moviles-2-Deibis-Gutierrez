package com.app.copamundialfifa2026.data.model

/**
 * Ticket priority. [weight] drives ordering in the list (higher = shown first), which is the core
 * of the reactive "priority update reorders the list" scenario.
 */
enum class TicketPriority(val weight: Int, val label: String) {
    CRITICAL(4, "Critical"),
    HIGH(3, "High"),
    MEDIUM(2, "Medium"),
    LOW(1, "Low")
}
