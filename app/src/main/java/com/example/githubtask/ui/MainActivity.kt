package com.example.githubtask.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtask.R
import com.example.githubtask.isNetworkConnected
import com.example.githubtask.models.GithubRepo
import com.example.githubtask.models.Request
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var reposAdapter: ReposAdapter
    private val reposList = ArrayList<GithubRepo>()
    private var loading = false
    var pastVisibleItems = 0
    var visibleItemCount = 0
    var totalItemCount = 0
    var lastVisibleItems = 0
    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupReposRecyclerView()
        fetchRepos()
        observeReposList()
    }

    private fun setupReposRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        reposRecycler.layoutManager = layoutManager

        reposAdapter = ReposAdapter(reposList)
        reposRecycler.adapter = reposAdapter

        reposRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = reposRecycler.layoutManager as LinearLayoutManager
                visibleItemCount = layoutManager.childCount
                totalItemCount = layoutManager.itemCount
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                lastVisibleItems = layoutManager.findLastVisibleItemPosition()
                if (dy > 0 && !loading && (visibleItemCount + pastVisibleItems >= totalItemCount)
                    && isNetworkConnected(this@MainActivity)
                ) {
                    loading = true
                    page++
                    fetchRepos()
                }
            }
        })
    }

    private fun fetchRepos() {
        lifecycleScope.launch {
            homeViewModel.fetchRepos(page, 15)
            loading = false
        }
    }

    private fun observeReposList() {
        homeViewModel.reposList?.observe(this) {
            when (it.status) {
                Request.Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                }

                Request.Status.ERROR -> {
                    progress.visibility = View.GONE
                    Toast.makeText(this, "Connection Error", Toast.LENGTH_SHORT).show()
                }

                Request.Status.SUCCESS -> {
                    progress.visibility = View.GONE
                    reposList.addAll(ArrayList(it.data))
                    reposAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}