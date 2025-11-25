package com.example.appscheduler._modules

import com.example.appscheduler.MainViewModel
import com.example.appscheduler.datasource.repo.AppScheduleLocalDs
import com.example.appscheduler.datasource.repo.AppScheduleLocalImpl
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module


val appModule = module {
    single <AppScheduleLocalDs> { AppScheduleLocalImpl(get()) }

    viewModel { MainViewModel(get()) }
}

val koinModules = module {
    includes(
        databaseModule,
        appModule
    )
}