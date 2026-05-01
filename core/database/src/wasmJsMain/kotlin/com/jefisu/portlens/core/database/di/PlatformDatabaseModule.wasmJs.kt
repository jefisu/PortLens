package com.jefisu.portlens.core.database.di

import androidx.room3.Room
import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import com.jefisu.portlens.core.database.PortLensDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import org.w3c.dom.Worker

actual val platformDatabaseModule: Module = module {
    single<PortLensDatabase> {
        Room.databaseBuilder<PortLensDatabase>(name = "portlens.db")
            .setDriver(WebWorkerSQLiteDriver(createSQLiteWorker()))
            .fallbackToDestructiveMigration(true)
            .build()
    }
}

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("() => new Worker(new URL('sqlite-wasm-worker/worker.js', import.meta.url), { type: 'module' })")
private external fun createSQLiteWorker(): Worker
