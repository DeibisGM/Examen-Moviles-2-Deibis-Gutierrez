package com.app.copamundialfifa2026.data.remote.dto

/** Request/response payloads for POST /auth/login (see /contracts/tickets-api.yaml). */
data class LoginRequest(
    val email: String,
    val password: String
)

data class UserDto(
    val id: String,
    val name: String,
    val email: String
)
