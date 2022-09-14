package com.flab.findmycat

import android.app.Application
import com.flab.findmycat.network.provideCatApi
import com.flab.findmycat.network.provideLoggingInterceptor
import com.flab.findmycat.network.provideOkHttpClient
import com.flab.findmycat.network.provideRetrofit
import com.flab.findmycat.ui.detail.CatDetailViewModel
import com.flab.findmycat.ui.list.CatsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {
    private val viewModules = module {
        viewModel { CatsViewModel(get()) }
        viewModel { CatDetailViewModel(get()) }
    }

    private val networkModule = module {
        factory { provideCatApi(get()) }
        factory { provideLoggingInterceptor() }
        factory { provideOkHttpClient(get()) }
        factory { provideRetrofit(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(networkModule, viewModules)
        }
    }
}
