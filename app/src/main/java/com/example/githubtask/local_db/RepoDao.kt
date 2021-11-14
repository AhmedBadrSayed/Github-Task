package com.example.githubtask.local_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubtask.models.GithubRepo

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repository: GithubRepo)

    @Update
    fun update(repository: GithubRepo)

    @Delete
    fun delete(repository: GithubRepo)

    @Query("delete from repos_table")
    fun deleteAllRepos()

    @Query("select * from repos_table")
    fun getAllRepos(): List<GithubRepo>
}