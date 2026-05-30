package com.app.copamundialfifa2026.data.remote

import com.app.copamundialfifa2026.core.AppConstants
import com.app.copamundialfifa2026.data.remote.dto.CreateTicketRequest
import com.app.copamundialfifa2026.data.remote.dto.TicketDto
import com.app.copamundialfifa2026.data.remote.dto.UpdatePriorityRequest
import com.app.copamundialfifa2026.data.remote.dto.UpdateStatusRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Ticket API contract. This interface mirrors /contracts/tickets-api.yaml and is the seam where a
 * real backend plugs in. In this PoC the repository serves mock data instead of calling these
 * methods, but the contract is already defined so integration is a drop-in change.
 */
interface TicketApiService {

    @GET(AppConstants.Api.Paths.TICKETS)
    suspend fun getTickets(): Response<List<TicketDto>>

    @GET(AppConstants.Api.Paths.TICKET_BY_ID)
    suspend fun getTicket(@Path("id") id: String): Response<TicketDto>

    @POST(AppConstants.Api.Paths.TICKETS)
    suspend fun createTicket(@Body request: CreateTicketRequest): Response<TicketDto>

    @PATCH(AppConstants.Api.Paths.TICKET_STATUS)
    suspend fun updateStatus(
        @Path("id") id: String,
        @Body request: UpdateStatusRequest
    ): Response<TicketDto>

    @PATCH(AppConstants.Api.Paths.TICKET_PRIORITY)
    suspend fun updatePriority(
        @Path("id") id: String,
        @Body request: UpdatePriorityRequest
    ): Response<TicketDto>
}
