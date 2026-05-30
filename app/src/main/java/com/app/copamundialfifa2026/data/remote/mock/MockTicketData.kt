package com.app.copamundialfifa2026.data.remote.mock

import com.app.copamundialfifa2026.data.model.Ticket
import com.app.copamundialfifa2026.data.model.TicketCategory
import com.app.copamundialfifa2026.data.model.TicketPriority
import com.app.copamundialfifa2026.data.model.TicketStatus

/**
 * Realistic seed data for the PoC. Every ticket reflects a plausible Panini support scenario
 * (provider issues, distribution, inventory shortages, logistics, packaging defects, payments)
 * for the FIFA World Cup 2026 album operation. No generic placeholders.
 */
object MockTicketData {

    fun seed(): List<Ticket> = listOf(
        Ticket(
            id = "TCK-1001",
            title = "Shortage of 1,200 sticker packs in batch LOT-2026-0457",
            description = "Warehouse Heredia received batch LOT-2026-0457 short by 1,200 packs. " +
                "Provider invoice lists the full quantity. Distribution to 14 retail points is on hold.",
            priority = TicketPriority.CRITICAL,
            status = TicketStatus.OPEN,
            provider = "Panini México",
            category = TicketCategory.INVENTORY_SHORTAGE,
            createdAt = "2026-05-28"
        ),
        Ticket(
            id = "TCK-1002",
            title = "Delivery delay to point of sale San José Centro",
            description = "Carrier missed the 48h SLA for route SJO-01. Retailer reports empty " +
                "shelves during launch week. Need rescheduled delivery and root-cause from carrier.",
            priority = TicketPriority.HIGH,
            status = TicketStatus.IN_PROGRESS,
            provider = "DHL Supply Chain",
            category = TicketCategory.LOGISTICS,
            createdAt = "2026-05-27"
        ),
        Ticket(
            id = "TCK-1003",
            title = "Duplicated stickers found in sealed display boxes (50u)",
            description = "Three sealed displays from Panini Brasil contain repeated sticker #312. " +
                "Quality flagged a printing collation issue. Awaiting replacement plan from provider.",
            priority = TicketPriority.MEDIUM,
            status = TicketStatus.OPEN,
            provider = "Panini Brasil",
            category = TicketCategory.PACKAGING,
            createdAt = "2026-05-26"
        ),
        Ticket(
            id = "TCK-1004",
            title = "Inventory mismatch at Alajuela warehouse cycle count",
            description = "Cycle count shows 380 albums fewer than the system. Possible mis-scan at " +
                "receiving or untracked transfer to Liberia hub. Reconciliation required.",
            priority = TicketPriority.HIGH,
            status = TicketStatus.OPEN,
            provider = "Distribuidora Centroamérica S.A.",
            category = TicketCategory.INVENTORY_SHORTAGE,
            createdAt = "2026-05-25"
        ),
        Ticket(
            id = "TCK-1005",
            title = "Provider has not confirmed restock of special edition stickers",
            description = "Special edition (gold) stickers are out of stock at 9 retail points. " +
                "Panini Italia has not confirmed the restock ETA requested last week.",
            priority = TicketPriority.MEDIUM,
            status = TicketStatus.OPEN,
            provider = "Panini Italia",
            category = TicketCategory.PROVIDER,
            createdAt = "2026-05-24"
        ),
        Ticket(
            id = "TCK-1006",
            title = "Pending payment to carrier for Caribbean route",
            description = "Carrier holds next dispatch until invoice INV-2026-0091 is settled. " +
                "Finance approval pending. Risk of distribution stop on the Limón corridor.",
            priority = TicketPriority.LOW,
            status = TicketStatus.RESOLVED,
            provider = "Transportes Limón",
            category = TicketCategory.PAYMENT,
            createdAt = "2026-05-22"
        ),
        Ticket(
            id = "TCK-1007",
            title = "Wrong albums distributed to northern region retailers",
            description = "Spanish-language albums were shipped to a batch of English-only retailers " +
                "in the northern zone. Swap logistics and corrected manifest needed.",
            priority = TicketPriority.HIGH,
            status = TicketStatus.IN_PROGRESS,
            provider = "Coopetransportes R.L.",
            category = TicketCategory.DISTRIBUTION,
            createdAt = "2026-05-21"
        ),
        Ticket(
            id = "TCK-1008",
            title = "Damaged packaging on 6 pallets received from port",
            description = "Water damage on 6 pallets from the Puerto Limón shipment. Provider claim " +
                "and replacement units required before redistribution.",
            priority = TicketPriority.MEDIUM,
            status = TicketStatus.OPEN,
            provider = "Panini España",
            category = TicketCategory.PACKAGING,
            createdAt = "2026-05-20"
        ),
        Ticket(
            id = "TCK-1009",
            title = "Closed: missing hardcover albums for premium kit",
            description = "Hardcover albums for the premium kit were located in a mislabeled pallet " +
                "and reassigned. Closed after reconciliation with the provider.",
            priority = TicketPriority.LOW,
            status = TicketStatus.CLOSED,
            provider = "Panini México",
            category = TicketCategory.INVENTORY_SHORTAGE,
            createdAt = "2026-05-18"
        )
    )
}
