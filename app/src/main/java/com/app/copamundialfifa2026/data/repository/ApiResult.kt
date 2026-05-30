package com.app.copamundialfifa2026.data.repository

/**
 * Typed result for repository operations. Keeps loading/success/error handling explicit and
 * uniform across ViewModels, and works identically whether data comes from mock or a real API.
 */
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}
