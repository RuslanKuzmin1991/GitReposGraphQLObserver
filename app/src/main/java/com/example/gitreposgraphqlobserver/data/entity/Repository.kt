package com.example.gitreposgraphqlobserver.data.entity

data class Repository(
    val name: String,
    val description: String,
    val stargazerCount: Int,
    val url: String,
    val language: String,
    val languageColor: String?,
    val updatedAt: String,
    val owner: String,
    val avatarUrl: String
)

data class PaginatedRepositories(
    val items: List<Repository>,
    val endCursor: String?,
    val hasNextPage: Boolean
)
