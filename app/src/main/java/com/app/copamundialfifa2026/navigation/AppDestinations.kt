package com.app.copamundialfifa2026.navigation

/** Navigation routes for the app. */
object AppDestinations {
    const val LOGIN = "login"
    const val TICKET_LIST = "tickets"
    const val TICKET_DETAIL = "tickets/{id}"
    const val CREATE_TICKET = "tickets/create"

    fun ticketDetailRoute(id: String): String = "tickets/$id"
}
