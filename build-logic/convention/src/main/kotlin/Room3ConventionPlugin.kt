import androidx.room3.gradle.RoomExtension
import com.jefisu.portlens.getPluginId
import com.jefisu.portlens.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class Room3ConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.getPluginId("ksp"))
            apply(libs.getPluginId("room3"))
        }

        extensions.configure<RoomExtension> {
            schemaDirectory("$projectDir/schemas")
        }

        dependencies {
            "commonMainImplementation"(project(":core:domain"))
            "commonMainImplementation"(libs.findLibrary("androidx-room3-runtime").get())

            "jvmMainImplementation"(libs.findLibrary("androidx-sqlite-bundled").get())
            "wasmJsMainImplementation"(libs.findLibrary("androidx-sqlite-web").get())

            "kspJvm"(libs.findLibrary("androidx-room3-compiler").get())
            "kspWasmJs"(libs.findLibrary("androidx-room3-compiler").get())
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.getByName("wasmJsMain").dependencies {
                implementation(npm("@sqlite.org/sqlite-wasm", "3.50.1-build1"))
                implementation(npm("sqlite-wasm-worker", target.file("sqlite-wasm-worker")))
            }
        }
    }
}
