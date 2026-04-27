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
import org.jetbrains.compose.desktop.DesktopExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

class ComposeMultiplatformApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.getPluginId("composeHotReload"))
            apply(libs.getPluginId("kotlinSerialization"))
            apply<ComposeMultiplatformFeatureConventionPlugin>()
            apply<DependencyGuardConventionPlugin>()
        }

        configureDesktop()

        dependencies {
            "commonMainImplementation"(libs.findLibrary("androidx-navigation-compose").get())

            "jvmMainImplementation"(libs.findLibrary("kotlinx-coroutinesSwing").get())
        }
    }
}

private fun Project.configureDesktop() {
    extensions.configure<ComposeExtension> {
        (this as ExtensionAware).extensions.configure<DesktopExtension>("desktop") {
            application {
                val appPackageName = libs.getVersion("appPackageName")
                mainClass = "$appPackageName.MainKt"

                nativeDistributions {
                    targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                    packageName = appPackageName
                    packageVersion = libs.getVersion("appPackageVersion")
                }
            }
        }
    }
}
