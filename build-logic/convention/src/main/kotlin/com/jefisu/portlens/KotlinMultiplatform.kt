package com.jefisu.portlens

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.configureKotlinMultiplatform(block: (KotlinMultiplatformExtension.() -> Unit)? = null) {
    extensions.configure<KotlinMultiplatformExtension> {
        jvm()

        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {
            browser()
            binaries.executable()
        }

        jvmToolchain(17)

        compilerOptions {
            freeCompilerArgs.addAll(
                "-Xexplicit-backing-fields",
                "-Xcontext-parameters",
            )
        }

        block?.invoke(this)
    }
}
