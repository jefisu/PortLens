package com.jefisu.portlens.di

import com.jefisu.portlens.feature.dashboard.presentation.di.dashboardPresentationModule
import org.koin.dsl.module

val portLensAppModule = module {
    includes(appModule, appShellModule, dashboardPresentationModule)
}
