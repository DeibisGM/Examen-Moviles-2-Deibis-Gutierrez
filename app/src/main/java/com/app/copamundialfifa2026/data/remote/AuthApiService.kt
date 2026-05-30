package com.app.copamundialfifa2026.data.remote

import com.app.copamundialfifa2026.core.AppConstants
import com.app.copamundialfifa2026.data.remote.dto.LoginRequest
import com.app.copamundialfifa2026.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/** Auth API contract (see /contracts/tickets-api.yaml). Served by mock data in this PoC. */
interface AuthApiService {

    @POST(AppConstants.Api.Paths.AUTH_LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<UserDto>
}
