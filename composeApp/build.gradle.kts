plugins {
    alias(libs.plugins.portlens.compose.multiplatform.application)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.dashboard.presentation)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}
