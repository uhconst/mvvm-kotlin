package com.constancio.di

import com.constancio.data.repository.CodeRepositoryImpl
import com.constancio.domain.interaction.CodeUseCase
import com.constancio.domain.repository.CodeRepository
import org.koin.dsl.module

val domainModule = module {

    single<CodeRepository> { CodeRepositoryImpl(get(), get()) }

    factory { CodeUseCase(get()) }
}