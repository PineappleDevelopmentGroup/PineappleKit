package sh.miles.pineapplekit

import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

class PineappleKitPlugin : Plugin<Project> {

    companion object {
        private const val PINEAPPLE_DEPENDENCY_PATTERN = "sh.miles:%s:%s"
        private const val SPIGOT_DEPENDENCY_PATTERN = "org.spigotmc:spigot-api:%s-R0.1-SNAPSHOT"
        private const val PINEAPPLE_REPOSITORY = "https://maven.miles.sh/pineapple"
        private const val SPIGOT_REPOSITORY = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
    }

    override fun apply(project: Project) {
        project.plugins.apply(KotlinPluginWrapper::class)
        project.plugins.apply(ShadowPlugin::class)
        val extension = project.extensions.create("pineappleKit", PineappleKitExtension::class.java, project.objects)

        project.afterEvaluate {
            repositories {
                mavenCentral()
                maven(PINEAPPLE_REPOSITORY)
                maven(SPIGOT_REPOSITORY)
            }

            dependencies {
                val compileOnly = configurations.getByName("compileOnly")
                val implementation = configurations.getByName("implementation")
                if (extension.spigotVersion.orNull != null) {
                    compileOnly(SPIGOT_DEPENDENCY_PATTERN.format(extension.spigotVersion.get()))
                    for (pineappleModule in extension.modules.get()) {
                        implementation(
                            PINEAPPLE_DEPENDENCY_PATTERN.format(
                                pineappleModule.name,
                                pineappleModule.version
                            )
                        )
                    }
                }
            }

            tasks.getByName("shadowJar", ShadowJar::class) {
                archiveClassifier = ""
                archiveVersion = ""
                archiveFileName = "${project.name}-${project.version}.jar"
                if (extension.mainPackage.orNull != null) {
                    relocate("sh.miles.pineapple.", "${extension.mainPackage.get()}.libs.pineapple.")
                }

                if (extension.modules.orNull != null) {
                    for (pineappleModule in extension.modules.get()) {
                        val pattern = pineappleModule.exclusionPattern
                        if (pattern.isNotEmpty()) {
                            pattern.forEach({ exclude("${it.replace(".", "/")}/**") })
                        }
                    }
                }
            }

            tasks.getByName("build") {
                dependsOn(tasks.getByName("shadowJar"))
            }

            configurations.all {
                resolutionStrategy.cacheChangingModulesFor(0, "seconds")
            }
        }
    }
}
