package com.flab.findmycat

import android.app.Application
import com.flab.findmycat.network.networkModule
import com.flab.findmycat.ui.detail.CatDetailFragment
import com.flab.findmycat.ui.detail.CatDetailViewModel
import com.flab.findmycat.ui.list.CatListFragment
import com.flab.findmycat.ui.list.CatListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {
    private val viewModules = module {
        viewModel { CatListViewModel(get()) }
        viewModel { CatDetailViewModel(get()) }
    }

    private val fragmentModule = module {
        factory { CatListFragment() }
        factory { CatDetailFragment() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(networkModule, fragmentModule, viewModules))
        }
    }
}
