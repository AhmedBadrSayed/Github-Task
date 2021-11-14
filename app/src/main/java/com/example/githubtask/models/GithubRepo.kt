package com.example.githubtask.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos_table")
data class GithubRepo(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    var url: String? = null,
    var author: String? = null,
    var name: String? = null,
    var avatar: String? = null,
    var description: String? = null,
    var language: String? = null,
    var languageColor: String? = null,
    var stars: String? = null,
    var forks: String? = null
)
