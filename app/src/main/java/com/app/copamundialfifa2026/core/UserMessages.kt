package com.app.copamundialfifa2026.core

/**
 * Single source of truth for user-visible strings. Keeping them here keeps the Composables clean
 * and makes copy review / future localization a one-file change.
 */
object UserMessages {

    object App {
        const val NAME = "Panini Support"
        const val TAGLINE = "Internal support tickets · FIFA World Cup 2026 album"
    }

    object Login {
        const val TITLE = "Panini Support"
        const val SUBTITLE = "Provider & logistics ticket desk"
        const val EMAIL_LABEL = "Corporate email"
        const val PASSWORD_LABEL = "Password"
        const val SIGN_IN = "Sign in"
        const val SIGNING_IN = "Signing in…"
        const val DEMO_CREDENTIALS = "Access credentials · support@panini.com / Panini2026"
        const val EMPTY_FIELDS = "Please enter your email and password."
        const val INVALID_CREDENTIALS = "Invalid credentials. Use the demo access shown below."
    }

    object TicketList {
        const val TITLE = "Support tickets"
        const val LOADING = "Loading tickets…"
        const val EMPTY = "No tickets to show yet."
        const val NEW_TICKET = "New ticket"
        const val SORTED_HINT = "Sorted by priority — highest first"
        const val LOGOUT = "Sign out"
    }

    object TicketDetail {
        const val TITLE = "Ticket detail"
        const val NOT_FOUND = "This ticket no longer exists."
        const val PROVIDER = "Provider"
        const val CATEGORY = "Category"
        const val CREATED = "Created on"
        const val DESCRIPTION = "Description"
        const val STATUS_SECTION = "Update status"
        const val PRIORITY_SECTION = "Update priority"
        const val STATUS_UPDATED = "Status updated"
        const val PRIORITY_UPDATED = "Priority updated — list reordered"
    }

    object CreateTicket {
        const val TITLE = "New ticket"
        const val TITLE_LABEL = "Title"
        const val DESCRIPTION_LABEL = "Description"
        const val PROVIDER_LABEL = "Related provider"
        const val CATEGORY_LABEL = "Category"
        const val PRIORITY_LABEL = "Priority"
        const val SUBMIT = "Create ticket"
        const val SUBMITTING = "Creating…"
        const val EMPTY_FIELDS = "Title, description and provider are required."
        const val CREATED = "Ticket created"
    }

    object Errors {
        const val GENERIC = "Something went wrong. Please try again."
        const val NETWORK = "Could not reach the server. Check your connection."
    }
}
