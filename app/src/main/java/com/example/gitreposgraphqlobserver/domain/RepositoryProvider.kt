package com.example.gitreposgraphqlobserver.domain

import com.example.gitreposgraphqlobserver.data.entity.PaginatedRepositories
import kotlinx.coroutines.flow.Flow

interface RepositoryProvider {
    suspend fun getRepositories(
        name: String,
        cursor: String?
    ): Flow<ResultWrapper<PaginatedRepositories, String>>
}