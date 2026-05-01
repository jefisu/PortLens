package com.jefisu.portlens.di

import com.jefisu.portlens.AppShellViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appShellModule = module {
    viewModelOf(::AppShellViewModel)
}
