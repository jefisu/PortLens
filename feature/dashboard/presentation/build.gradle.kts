plugins {
    alias(libs.plugins.portlens.compose.multiplatform.library)
}

compose.resources {
    packageOfResClass = "com.jefisu.portlens.feature.dashboard.presentation.generated.resources"
    publicResClass = true
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.designSystem)
            implementation(projects.core.domain)
            implementation(projects.core.presentation)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }
    }
}
