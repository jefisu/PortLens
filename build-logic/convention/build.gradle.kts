import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.jefisu.portlens.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.composeCompilerGradlePlugin)
    compileOnly(libs.dependencyGuard.gradlePlugin)
    compileOnly(libs.room3.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatformLibrary") {
            id = "portlens.kotlin.multiplatform.library"
            implementationClass = "KotlinMultiplatformLibraryConventionPlugin"
        }
        register("composeMultiplatformLibrary") {
            id = "portlens.compose.multiplatform.library"
            implementationClass = "ComposeMultiplatformLibraryConventionPlugin"
        }
        register("composeMultiplatformFeature") {
            id = "portlens.compose.multiplatform.feature"
            implementationClass = "ComposeMultiplatformFeatureConventionPlugin"
        }
        register("composeMultiplatformApplication") {
            id = "portlens.compose.multiplatform.application"
            implementationClass = "ComposeMultiplatformApplicationConventionPlugin"
        }
        register("dependencyGuard") {
            id = "portlens.dependency-guard"
            implementationClass = "DependencyGuardConventionPlugin"
        }
        register("koin") {
            id = "portlens.koin"
            implementationClass = "KoinConventionPlugin"
        }
        register("gitHooks") {
            id = "portlens.git-hooks"
            implementationClass = "GitHooksConventionPlugin"
        }
        register("room3") {
            id = "portlens.room3"
            implementationClass = "Room3ConventionPlugin"
        }
    }
}
