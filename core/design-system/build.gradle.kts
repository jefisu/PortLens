plugins {
    alias(libs.plugins.portlens.compose.multiplatform.library)
}

compose.resources {
    packageOfResClass = "com.jefisu.portlens.designsystem.generated.resources"
    publicResClass = true
}
