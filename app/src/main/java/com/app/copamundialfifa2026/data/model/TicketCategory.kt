package com.app.copamundialfifa2026.data.model

/** Type of incident a ticket reports, aligned with Panini's internal support taxonomy. */
enum class TicketCategory(val label: String) {
    INVENTORY_SHORTAGE("Inventory shortage"),
    DISTRIBUTION("Distribution"),
    LOGISTICS("Logistics"),
    PROVIDER("Provider"),
    PACKAGING("Packaging defect"),
    PAYMENT("Payment")
}
