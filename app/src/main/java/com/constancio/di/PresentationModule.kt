package com.constancio.di

import com.constancio.presentation.code.CodeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    /** Code View Model */
    viewModel { CodeViewModel(get()) }
}