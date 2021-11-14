package com.example.githubtask.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.githubtask.models.GithubRepo

@Database(entities = [GithubRepo::class], version = 1)
abstract class ReposDatabase : RoomDatabase() {

    abstract fun reposDao(): RepoDao

    companion object {
        private var instance: ReposDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): ReposDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, ReposDatabase::class.java,
                    "repos_database")
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!
        }
    }
}