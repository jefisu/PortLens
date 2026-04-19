import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        with(pluginManager) {
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("composeCompiler").get().get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            jvmToolchain(17)
            jvm()
            js { browser() }
            @OptIn(ExperimentalWasmDsl::class)
            wasmJs { browser() }

            sourceSets.named("commonMain") {
                dependencies {
                    implementation(libs.findLibrary("compose-runtime").get())
                    implementation(libs.findLibrary("compose-foundation").get())
                    api(libs.findLibrary("compose-material3").get())
                    implementation(libs.findLibrary("compose-ui").get())
                    implementation(libs.findLibrary("compose-components-resources").get())
                    implementation(libs.findLibrary("compose-uiToolingPreview").get())
                }
            }
        }
    }
}
