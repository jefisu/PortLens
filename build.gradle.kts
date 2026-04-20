plugins {
    // Prevent plugins from being loaded multiple times across sub-project classloaders
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.dependencyGuard) apply false

    // Module dependency graph — applied here so it can scan all subprojects
    alias(libs.plugins.moduleGraph) apply true

    // Git hooks — installed automatically on build/clean/check/assemble
    alias(libs.plugins.portlens.git.hooks)
}

// Prints every leaf-module path; consumed by generateModuleGraphs.sh
tasks.register("printModulePaths") {
    subprojects {
        if (subprojects.isEmpty()) println(path)
    }
}

// Configure which configurations moduleGraph should traverse for KMP projects
moduleGraphAssert {
    configurations += setOf(
        "commonMainImplementation",
        "commonMainApi",
        "jvmMainImplementation",
        "jvmMainApi",
        "jsMainImplementation",
        "jsMainApi",
        "wasmJsMainImplementation",
        "wasmJsMainApi",
    )
}
