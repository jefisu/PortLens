package com.jefisu.portlens.di

import com.jefisu.portlens.core.database.di.coreDatabaseModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    includes(coreDatabaseModule)
}
