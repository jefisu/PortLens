import com.jefisu.portlens.getPluginId
import com.jefisu.portlens.getVersion
import com.jefisu.portlens.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.resources.ResourcesExtension

class ComposeMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply<KotlinMultiplatformLibraryConventionPlugin>()
            apply(libs.getPluginId("composeMultiplatform"))
            apply(libs.getPluginId("composeCompiler"))
        }

        configureComposeResources()

        dependencies {
            "commonMainImplementation"(libs.findLibrary("compose-runtime").get())
            "commonMainImplementation"(libs.findLibrary("compose-foundation").get())
            "commonMainImplementation"(libs.findLibrary("compose-ui").get())
            "commonMainImplementation"(libs.findLibrary("compose-components-resources").get())
            "commonMainImplementation"(libs.findLibrary("compose-uiToolingPreview").get())
            "commonMainApi"(libs.findLibrary("compose-material3").get())
        }
    }
}

private val Project.modulePathToPackage: String
    get() = path
        .removePrefix(":")
        .replace(":", ".")
        .replace("-", "")

private fun Project.configureComposeResources() {
    extensions.configure<ComposeExtension> {
        (this as ExtensionAware).extensions.configure<ResourcesExtension> {
            generateResClass = ResourcesExtension.ResourceClassGeneration.Auto
            publicResClass = path == ":core:design-system"
            packageOfResClass =
                "${libs.getVersion("appPackageName")}.$modulePathToPackage.generated.resources"
        }
    }
}
