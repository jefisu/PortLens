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
}

gradlePlugin {
    plugins {
        register("composeMultiplatformLibrary") {
            id = "portlens.compose-multiplatform-library"
            implementationClass = "ComposeMultiplatformLibraryConventionPlugin"
        }
        register("dependencyGuard") {
            id = "portlens.dependency-guard"
            implementationClass = "DependencyGuardConventionPlugin"
        }
        register("gitHooks") {
            id = "portlens.git-hooks"
            implementationClass = "GitHooksConventionPlugin"
        }
    }
}
