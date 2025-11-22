package com.example.appscheduler._modules

import com.example.appscheduler.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf

import org.koin.dsl.module


val appModule = module {
    viewModelOf(::MainViewModel)
}

val koinModules = module {
    includes(
        appModule
    )
}