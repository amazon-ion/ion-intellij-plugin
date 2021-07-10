import plugin.PluginDescriptor
import plugin.PlatformType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("jvm")
    id("org.jetbrains.intellij") version "1.1.2"
}

repositories {
    mavenCentral()
}

val plugins = listOf(
    PluginDescriptor(
        since = "201",
        until = "203.*",
        sdkVersion = "IC-2020.1",
        platformType = PlatformType.IdeaCommunity,
        dependencies = listOf("java", "Kotlin")
    ),
    PluginDescriptor(
        since = "211",
        until = "212.*",
        sdkVersion = "IC-2021.1",
        platformType = PlatformType.IdeaCommunity,
        dependencies = listOf("java", "Kotlin")
    )
)

val productName = System.getenv("PRODUCT_NAME") ?: "IC-2020.1"
val descriptor = plugins.first { it.sdkVersion == productName }

logger.lifecycle("Building Plugin Distribution for ${descriptor.platformType} ${descriptor.sdkVersion}")

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

dependencies {
    implementation(kotlin("stdlib"))
}

intellij {
    pluginName.set(pluginName_)
    version.set(descriptor.sdkVersion)
    type.set(platformType)
    downloadSources.set(platformDownloadSources.toBoolean())
    updateSinceUntilBuild.set(false)

    // Plugin Dependencies -> https://www.jetbrains.org/intellij/sdk/docs/basics/plugin_structure/plugin_dependencies.html
    // Example: platformPlugins = com.intellij.java, com.jetbrains.php:203.4449.22
    plugins.set(descriptor.dependencies)
}

tasks {
    // Disable searchable options since it is failing during the build
    // Re-evaluate in the future if it starts to succeed.
    findByName("buildSearchableOptions")?.enabled = false

    patchPluginXml {
        version.set("2.0")
        sinceBuild.set(pluginSinceBuild)
        untilBuild.set(pluginUntilBuild)

        pluginDescription.set(readResource(pluginDescriptionFile))
        changeNotes.set(readResource(pluginChangeNotesFile))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    task("release") {
        dependsOn("buildPlugin")
    }
}

/**
 * Utility function to read a resource file.
 */
fun readResource(name: String) = file("resources/$name").readText()
