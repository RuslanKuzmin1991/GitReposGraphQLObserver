package com.example.gitreposgraphqlobserver.domain

sealed class ResultWrapper<T, E>() {
    class Loading<T, E> : ResultWrapper<T, E>()
    data class Success<T, E>(val data: T) : ResultWrapper<T, E>()
    data class Failure<T, E>(val error: E) : ResultWrapper<T, E>()
}