package com.app.copamundialfifa2026.data.repository

import com.app.copamundialfifa2026.core.AppConstants
import com.app.copamundialfifa2026.core.UserMessages
import com.app.copamundialfifa2026.data.remote.dto.UserDto
import kotlinx.coroutines.delay

/**
 * Simulated authentication. Validates against the fixed demo credentials and returns an
 * [ApiResult] so the login screen renders proper loading/success/error states. A real backend
 * would replace the body with an [com.app.copamundialfifa2026.data.remote.AuthApiService] call.
 */
class AuthRepository {

    suspend fun login(email: String, password: String): ApiResult<UserDto> {
        delay(AppConstants.Api.MOCK_LATENCY_MS)
        val matches = email.equals(AppConstants.MockAuth.EMAIL, ignoreCase = true) &&
            password == AppConstants.MockAuth.PASSWORD
        return if (matches) {
            ApiResult.Success(
                UserDto(id = "USR-001", name = "Support Agent", email = AppConstants.MockAuth.EMAIL)
            )
        } else {
            ApiResult.Error(UserMessages.Login.INVALID_CREDENTIALS)
        }
    }
}
