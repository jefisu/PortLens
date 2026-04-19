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
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.composeCompilerGradlePlugin)
}

gradlePlugin {
    plugins {
        register("composeMultiplatformLibrary") {
            id = "portlens.compose-multiplatform-library"
            implementationClass = "ComposeMultiplatformLibraryConventionPlugin"
        }
    }
}
