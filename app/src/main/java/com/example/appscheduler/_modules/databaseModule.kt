package com.example.appscheduler._modules

import androidx.room.Room
import com.example.appscheduler.datasource.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_schedule_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().scheduleDao() }
}