package com.app.copamundialfifa2026.core

/**
 * Centralized feature flags for the PoC.
 *
 * Toggling a flag here enables/disables a feature across the whole app without touching the
 * screens that consume it. This is the single place the team flips behavior during internal
 * testing. In a later phase these constants would be backed by a remote config provider
 * (e.g. Firebase Remote Config) without changing any consumer.
 */
object FeatureFlags {

    /** When false, the "create ticket" entry point (FAB + route) is hidden. */
    const val CREATE_TICKET_ENABLED = true

    /** When false, the priority control on the detail screen is hidden (read-only priority). */
    const val PRIORITY_UPDATE_ENABLED = true

    /** When false, resolved/closed tickets are filtered out of the main list. */
    const val SHOW_RESOLVED_TICKETS = true
}
