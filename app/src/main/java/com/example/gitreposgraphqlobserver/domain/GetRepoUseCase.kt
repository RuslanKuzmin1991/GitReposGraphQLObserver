package com.example.gitreposgraphqlobserver.domain

import com.example.gitreposgraphqlobserver.data.entity.PaginatedRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetRepoUseCase {
    suspend operator fun invoke(
        name: String,
        cursor: String?
    ): Result<PaginatedRepositories>
}

class GetRepoUseCaseImpl @Inject constructor(private val provider: RepositoryProvider) :
    GetRepoUseCase {
    override suspend operator fun invoke(
        name: String,
        cursor: String?
    ): Result<PaginatedRepositories>
    {
        val result = withContext(Dispatchers.IO) {
            provider.getRepositories(name, cursor)
        }
        return result.single()

    }
}