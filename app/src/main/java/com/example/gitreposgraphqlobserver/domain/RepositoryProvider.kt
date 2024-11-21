package com.example.gitreposgraphqlobserver.domain

import com.example.gitreposgraphqlobserver.data.entity.PaginatedRepositories

interface RepositoryProvider {
    suspend fun getRepositories(
        name: String,
        cursor: String?
    ): Result<PaginatedRepositories>
}