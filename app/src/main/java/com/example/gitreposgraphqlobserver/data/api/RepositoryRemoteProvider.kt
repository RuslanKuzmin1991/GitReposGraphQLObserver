package com.example.gitreposgraphqlobserver.data.api

import com.example.gitreposgraphqlobserver.data.entity.PaginatedRepositories
import com.example.gitreposgraphqlobserver.data.entity.toRepository
import com.example.gitreposgraphqlobserver.domain.RepositoryProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryRemoteProvider @Inject constructor(private val api: RepositoryApi) :
    RepositoryProvider {
    override suspend fun getRepositories(
        name: String,
        cursor: String?
    ): Flow<Result<PaginatedRepositories>> {
        return flow {
            when (val response = api.fetchRepositories(name, cursor)) {
                is ApiResponse.Failure -> {
                    emit(
                        Result.failure<PaginatedRepositories>(
                            exception = Exception(
                                response.error?.message ?: "Error"
                            )
                        )
                    )
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
                    emit(Result.success(repos))
                }
            }
        }
    }
}