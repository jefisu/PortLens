plugins {
    alias(libs.plugins.portlens.compose.multiplatform.library)
}

dependencies {
    commonMainImplementation(projects.core.domain)
    commonMainImplementation(libs.androidx.lifecycle.viewmodel)
}
