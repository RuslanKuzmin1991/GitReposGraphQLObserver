package com.example.gitreposgraphqlobserver.data.api

sealed class ApiResponse<T> {
    data class Failure<T>(
        val error: Exception? = null,
    ) : ApiResponse<T>()

    data class Success<T>(
        val body: T,
    ) : ApiResponse<T>()

    companion object {
        fun <T> from(data: T?) = data?.let { Success(data) } ?: Failure()
    }
}