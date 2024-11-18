package com.example.gitreposgraphqlobserver.data.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.gitreposgraphqlobserver.RepositoriesQuery

class RepositoryApiGraphQL(
    private val appoloClient: ApolloClient,
) : RepositoryApi {
    override suspend fun fetchRepositories(
        searchQuery: String,
        cursor: String?
    ): ApiResponse<RepositoriesQuery.Data> {
        val query = RepositoriesQuery(searchQuery, limit = Optional.present(50), cursor = Optional.present(cursor))
        val response = appoloClient
            .query(query)
            .execute()
        return if (response.hasErrors()) {
            ApiResponse.Failure(response.exception)
        } else {
            ApiResponse.from(response.data)
        }
    }
}
