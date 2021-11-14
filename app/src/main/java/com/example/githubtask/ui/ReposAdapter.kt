package com.example.githubtask.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtask.R
import com.example.githubtask.models.GithubRepo
import kotlinx.android.synthetic.main.repo_item.view.*

class ReposAdapter(
    private val reposList: List<GithubRepo>
) : RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        return ReposViewHolder(v)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.itemView.apply {
            title.text = reposList[position].name
            description.text = reposList[position].description
        }
    }

    override fun getItemCount(): Int {
        return reposList.size
    }

    inner class ReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}