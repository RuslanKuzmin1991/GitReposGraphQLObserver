package com.example.gitreposgraphqlobserver.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.example.gitreposgraphqlobserver.BuildConfig
import com.example.gitreposgraphqlobserver.data.api.RepositoryApi
import com.example.gitreposgraphqlobserver.data.api.RepositoryApiGraphQL
import com.example.gitreposgraphqlobserver.data.api.RepositoryRemoteProvider
import com.example.gitreposgraphqlobserver.domain.RepositoryProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                println("Request: ${request.url}")
                println("Headers: ${request.headers}")
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(okHttpClient)
            .addHttpHeader(
                name = "Authorization",
                "Bearer ${BuildConfig.GITHUB_TOKEN}",
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideRepositoryApi(apolloClient: ApolloClient): RepositoryApi {
        return RepositoryApiGraphQL(apolloClient)
    }

    @Singleton
    @Provides
    fun provideRepositoriesProvider(api: RepositoryApi): RepositoryProvider {
        return RepositoryRemoteProvider(api)
    }
}