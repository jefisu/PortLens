import com.jefisu.portlens.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class ComposeMultiplatformFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        apply<ComposeMultiplatformLibraryConventionPlugin>()
        apply<KoinConventionPlugin>()
        apply<DependencyGuardConventionPlugin>()

        dependencies {
            "commonMainApi"(project(":core:domain"))
            "commonMainApi"(project(":core:presentation"))
            "commonMainApi"(project(":core:design-system"))

            "commonMainImplementation"(
                libs.findLibrary("androidx-lifecycle-viewmodelCompose").get(),
            )
            "commonMainImplementation"(libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
            "commonMainImplementation"(libs.findLibrary("androidx-lifecycle-viewmodel").get())
        }
    }
}
