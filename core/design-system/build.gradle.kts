plugins {
    alias(libs.plugins.portlens.compose.multiplatform.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.presentation)
        }
    }
}
