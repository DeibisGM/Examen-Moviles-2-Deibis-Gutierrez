package com.app.copamundialfifa2026.core

/**
 * App-wide constants. Endpoint paths are kept in sync with /contracts/tickets-api.yaml so the
 * Retrofit layer already matches the documented API contract for a future real backend.
 */
object AppConstants {

    object Api {
        // Placeholder base URL. The PoC runs on mock data, so no real call is made yet.
        const val BASE_URL = "https://api.panini-support.example.com/"

        object Paths {
            const val AUTH_LOGIN = "auth/login"
            const val TICKETS = "tickets"
            const val TICKET_BY_ID = "tickets/{id}"
            const val TICKET_STATUS = "tickets/{id}/status"
            const val TICKET_PRIORITY = "tickets/{id}/priority"
        }

        // Simulated network latency so loading/success/error states are exercised in the UI.
        const val MOCK_LATENCY_MS = 600L
    }

    object MockAuth {
        const val EMAIL = "support@panini.com"
        const val PASSWORD = "Panini2026"
    }
}
