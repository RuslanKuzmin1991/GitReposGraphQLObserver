package com.example.gitreposgraphqlobserver.data.api

import com.example.gitreposgraphqlobserver.data.entity.PaginatedRepositories
import com.example.gitreposgraphqlobserver.data.entity.toRepository
import com.example.gitreposgraphqlobserver.domain.RepositoryProvider
import com.example.gitreposgraphqlobserver.domain.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryRemoteProvider(private val api: RepositoryApi) : RepositoryProvider {
    override suspend fun getRepositories(
        name: String,
        cursor: String?
    ): Flow<ResultWrapper<PaginatedRepositories, String>> {
        return flow {
            emit(ResultWrapper.Loading())
            val response = api.fetchRepositories(name, cursor)
            when (response) {
                is ApiResponse.Failure -> {
                    emit(ResultWrapper.Failure(error = response.error?.message ?: "Error"))
                    emit(ResultWrapper.Loading())
                }

                is ApiResponse.Success -> {
                    val body = response.body
                    val data = body.search
                    val repositories = data.nodes?.filterNotNull()?.mapNotNull {
                        val onRepo = it.onRepository
                        onRepo?.toRepository()
                    } ?: emptyList()

                    val repos = PaginatedRepositories(
                        items = repositories,
                        endCursor = data.pageInfo.endCursor,
                        hasNextPage = data.pageInfo.hasNextPage
                    )
                    emit(ResultWrapper.Success(repos))
                }
            }
        }
    }
}