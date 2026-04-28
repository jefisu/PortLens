import com.jefisu.portlens.configureKotlinMultiplatform
import com.jefisu.portlens.getPluginId
import com.jefisu.portlens.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.getPluginId("kotlinMultiplatform"))

        configureKotlinMultiplatform()

        dependencies {
            "commonMainImplementation"(libs.findLibrary("kotlinx-coroutines-core").get())
            "commonMainImplementation"(libs.findLibrary("kotlinx-datetime").get())
            "commonMainImplementation"(libs.findLibrary("kotlinx-serialization-core").get())

            "commonTestImplementation"(libs.findLibrary("kotlin-test").get())
            "jvmTestImplementation"(libs.findLibrary("kotlin-testJunit").get())
        }
    }
}
