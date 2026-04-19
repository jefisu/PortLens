import com.dropbox.gradle.plugins.dependencyguard.DependencyGuardPluginExtension
import com.jefisu.portlens.getPluginId
import com.jefisu.portlens.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Tracks the resolved JVM runtime classpath of :composeApp so that any
 * unintended dependency change is caught before it reaches a commit.
 *
 * Run `./gradlew dependencyGuardBaseline` to regenerate the baseline after
 * an intentional dependency update.
 */
class DependencyGuardConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.getPluginId("dependencyGuard"))

        extensions.configure<DependencyGuardPluginExtension> {
            // jvmRuntimeClasspath covers the desktop-JVM build — the primary
            // distribution target for PortLens v1.
            configuration("jvmRuntimeClasspath") {
                modules = true
                tree = true
            }
        }
    }
}
