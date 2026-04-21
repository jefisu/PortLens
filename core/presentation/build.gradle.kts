plugins {
    alias(libs.plugins.portlens.compose.multiplatform.library)
}

compose.resources {
    packageOfResClass = "com.jefisu.portlens.core.presentation.generated.resources"
    publicResClass = true
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(libs.kotlinx.datetime)
        }
    }
}
