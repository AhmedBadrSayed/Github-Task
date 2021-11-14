package com.example.githubtask.repository

import com.example.githubtask.api.GithubReposService
import com.example.githubtask.local_db.RepoDao
import com.example.githubtask.local_db.ReposDatabase
import com.example.githubtask.models.GithubRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class GithubRepository(
    private val githubReposService: GithubReposService,
    reposDatabase: ReposDatabase
) {

    private var reposDao: RepoDao = reposDatabase.reposDao()

    fun fetchRepos(page: Int, limit: Int): Flow<List<GithubRepo>> {
        return flow {
            val result = githubReposService.fetchRepos(page, limit)
            result.forEach {
                reposDao.insert(it)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun fetchLocalRepos() : List<GithubRepo> {
        return reposDao.getAllRepos()
    }
}