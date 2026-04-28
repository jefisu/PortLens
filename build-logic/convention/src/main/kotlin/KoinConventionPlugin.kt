import com.jefisu.portlens.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        apply<DependencyGuardConventionPlugin>()

        dependencies {
            "commonMainImplementation"(libs.findLibrary("koin-core").get())
            "commonMainImplementation"(libs.findLibrary("koin-compose").get())
            "commonMainImplementation"(libs.findLibrary("koin-compose-viewmodel").get())
        }
    }
}
