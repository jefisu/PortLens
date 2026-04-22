plugins {
    alias(libs.plugins.portlens.compose.feature)
}

compose.resources {
    packageOfResClass = "com.jefisu.portlens.feature.dashboard.presentation.generated.resources"
    publicResClass = true
}
