package com.constancio.di

import com.constancio.BuildConfig
import com.constancio.data.local.db.AppDatabase
import com.constancio.data.remote.CodeService
import com.constancio.data.remote.interceptor.RemoteRequestInterceptor
import com.constancio.data.remote.interceptor.RxRemoteErrorInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    factory { RxRemoteErrorInterceptor() }

    factory { RemoteRequestInterceptor() }

    single {
        AppDatabase.createDatabase(
            androidApplication(),
            "AppDatabase" //todo: Should be more easy to maintain
        )
    }

    single { get<AppDatabase>().codeDao() }

    single {
        CodeService.createCodeService(
            BuildConfig.API_URL,
            get(),
            get()
        )
    }
}