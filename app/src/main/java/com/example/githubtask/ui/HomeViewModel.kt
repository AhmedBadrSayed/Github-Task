package com.example.githubtask.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.githubtask.models.GithubRepo
import com.example.githubtask.models.Request
import com.example.githubtask.repository.GithubRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val githubRepository: GithubRepository) : ViewModel() {
    private var _reposList = MutableLiveData<Request<List<GithubRepo>>>()
    val reposList: LiveData<Request<List<GithubRepo>>>?
        get() = _reposList

    suspend fun fetchRepos(page: Int, limit: Int) {
        githubRepository.fetchRepos(page, limit)
            .onStart { _reposList.value = Request.loading() }
            .catch { exception ->
                Log.e(
                    HomeViewModel::class.simpleName,
                    "fetchRepos: ",
                    exception.cause
                )
                _reposList.value = Request.error(exception.cause?.message)
                fetchLocalRepos()
            }
            .collect { result ->
                _reposList.value = Request.success(result)
            }
    }

    private fun fetchLocalRepos() {
        CoroutineScope(Dispatchers.IO).launch {
            val reposList = githubRepository.fetchLocalRepos()
            withContext(Dispatchers.Main) {
                _reposList.value = Request.success(reposList)
            }
        }
    }
}