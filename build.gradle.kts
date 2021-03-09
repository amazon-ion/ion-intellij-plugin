import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    id("org.jetbrains.intellij") version "0.7.2"
}

// Import variables from gradle.properties file

val pluginGroup: String by project

// `pluginName_` variable ends with `_` because of the collision with Kotlin magic getter in the `intellij` closure.
// Read more about the issue: https://github.com/JetBrains/intellij-platform-plugin-template/issues/29
val pluginName_: String by project
val pluginSinceBuild: String by project
val pluginUntilBuild: String by project
val pluginVerifierIdeVersions: String by project
val pluginDescriptionFile: String by project
val pluginChangeNotesFile: String by project

val platformType: String by project
val platformVersion: String by project
val platformPlugins: String by project
val platformDownloadSources: String by project

val packageVersion: String by project

group = pluginGroup
version = packageVersion

tasks.withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
}

sourceSets["main"].java.srcDirs("src/main/gen")

listOf("compileKotlin", "compileTestKotlin").forEach {
    tasks.getByName<KotlinCompile>(it) {
        kotlinOptions.jvmTarget = "11"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

intellij {
    pluginName = pluginName_
    version = platformVersion
    type = platformType
    downloadSources = platformDownloadSources.toBoolean()
    updateSinceUntilBuild = false

    // Plugin Dependencies -> https://www.jetbrains.org/intellij/sdk/docs/basics/plugin_structure/plugin_dependencies.html
    // Example: platformPlugins = com.intellij.java, com.jetbrains.php:203.4449.22
    setPlugins("java", "Kotlin")
}

tasks {
    // Disable searchable options since it is failing during the build
    // Re-evaluate in the future if it starts to succeed.
    findByName("buildSearchableOptions")?.enabled = false

    patchPluginXml {
        version("2.0")
        sinceBuild(pluginSinceBuild)
        untilBuild(pluginUntilBuild)

        pluginDescription(readResource(pluginDescriptionFile))
        changeNotes(readResource(pluginChangeNotesFile))
    }

    task("release") {
        dependsOn("buildPlugin")
    }
}

/**
 * Utility function to read a resource file.
 */
fun readResource(name: String) = file("resources/$name").readText()
