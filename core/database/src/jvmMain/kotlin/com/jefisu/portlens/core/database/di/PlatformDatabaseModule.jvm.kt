package com.jefisu.portlens.core.database.di

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.jefisu.portlens.core.database.PortLensDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDatabaseModule: Module = module {
    single<PortLensDatabase> {
        Room.databaseBuilder<PortLensDatabase>(name = "portlens.db")
            .setDriver(BundledSQLiteDriver())
            .fallbackToDestructiveMigration(true)
            .build()
    }
}
