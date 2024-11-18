package com.example.gitreposgraphqlobserver.data.entity

import com.example.gitreposgraphqlobserver.RepositoriesQuery

fun RepositoriesQuery.OnRepository.toRepository(): Repository {
    return Repository(
        name = this.name,
        description = this.shortDescriptionHTML.toString(),
        stargazerCount = this.stargazerCount,
        url = this.url.toString(),
        language = this.primaryLanguage.toString(),
        languageColor = this.primaryLanguage?.color.toString(),
        updatedAt = this.updatedAt.toString(),
        owner = this.owner.login,
        avatarUrl = this.owner.avatarUrl.toString()
    )
}