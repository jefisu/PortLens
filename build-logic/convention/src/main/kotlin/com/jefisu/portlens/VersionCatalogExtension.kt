package com.jefisu.portlens

import org.gradle.api.artifacts.VersionCatalog

fun VersionCatalog.getPluginId(alias: String): String = findPlugin(alias).get().get().pluginId

fun VersionCatalog.getVersion(alias: String): String = findVersion(alias).get().requiredVersion
