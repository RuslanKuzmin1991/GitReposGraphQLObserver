package com.example.gitreposgraphqlobserver.presenter.screen

import com.example.gitreposgraphqlobserver.data.entity.Repository

data class UiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<Repository> = emptyList(),
    val hasNextPage: Boolean = true,
    val cursor: String? = null
)
