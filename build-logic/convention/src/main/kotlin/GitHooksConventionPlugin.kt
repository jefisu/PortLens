import java.util.Locale
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.register

/**
 * Installs the git hooks defined in /scripts into .git/hooks.
 * Hooks are automatically re-installed whenever the project is built or cleaned.
 */
class GitHooksConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        fun isUnix(): Boolean {
            val os = System.getProperty("os.name").lowercase(Locale.getDefault())
            return os.contains("linux") || os.contains("mac")
        }

        project.tasks.register<Copy>("copyGitHooks") {
            description = "Copies git hook scripts from /scripts to .git/hooks."
            from("${project.rootDir}/scripts/") {
                include("**/*.sh")
                rename { it.removeSuffix(".sh") }
            }
            into("${project.rootDir}/.git/hooks")
        }

        project.tasks.register<Exec>("installGitHooks") {
            description = "Makes copied hooks executable."
            group = "git hooks"
            workingDir = project.rootDir
            commandLine(
                if (isUnix()) {
                    listOf("chmod", "-R", "+x", ".git/hooks/")
                } else {
                    listOf("cmd", "/c", "attrib", "-R", "+X", ".git/hooks/*.*")
                },
            )
            dependsOn("copyGitHooks")
            doLast { logger.lifecycle("Git hooks installed successfully.") }
        }

        project.afterEvaluate {
            tasks.matching { it.name in setOf("build", "assemble", "clean", "check") }
                .configureEach { dependsOn("installGitHooks") }
        }
    }
}
