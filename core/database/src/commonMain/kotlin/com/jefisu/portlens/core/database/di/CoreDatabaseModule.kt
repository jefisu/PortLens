package com.jefisu.portlens.core.database.di

import com.jefisu.portlens.core.database.repository.RoomDashboardRepository
import com.jefisu.portlens.core.domain.DashboardRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDatabaseModule = module {
    includes(platformDatabaseModule)

    singleOf(::RoomDashboardRepository) bind DashboardRepository::class
}
