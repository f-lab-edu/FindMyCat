package com.flab.findmycat.application

import android.app.Application
import com.flab.findmycat.data.database.AppDatabase
import com.flab.findmycat.data.network.GithubApi
import com.flab.findmycat.data.repository.SearchRepository
import com.flab.findmycat.ui.search.SearchViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { GithubApi() }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { SearchRepository(instance(), instance(), instance()) }
        bind() from provider { SearchViewModelFactory(instance()) }
    }
}
