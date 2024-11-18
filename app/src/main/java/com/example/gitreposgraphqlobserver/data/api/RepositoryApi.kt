package com.example.gitreposgraphqlobserver.data.api

import com.example.gitreposgraphqlobserver.RepositoriesQuery

interface RepositoryApi {
    suspend fun fetchRepositories(
        searchQuery: String,
        cursor: String?
    ): ApiResponse<RepositoriesQuery.Data>
}