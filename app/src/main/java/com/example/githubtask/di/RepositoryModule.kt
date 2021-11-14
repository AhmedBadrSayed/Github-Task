package com.example.githubtask.di

import com.example.githubtask.local_db.ReposDatabase
import com.example.githubtask.repository.GithubRepository
import com.google.android.material.internal.ContextUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repoModule = module {
    single {
        ReposDatabase.getInstance(androidContext())
    }

    single {
        GithubRepository(get(), get())
    }
}