import plugin.PluginDescriptor
import plugin.PluginDescriptor.KotlinOptions
import plugin.PlatformType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("jvm")
    id("org.jetbrains.intellij") version "1.12.0"
}

repositories {
    mavenCentral()
}

val plugins = listOf(
    PluginDescriptor(
        since = "222",
        until = "223.*",
        sdkVersion = "IC-2022.2",
        platformType = PlatformType.IdeaCommunity,
        sourceFolder = "IC-222",
        kotlin = KotlinOptions(
            apiVersion = "1.6"
        ),
        dependencies = listOf("java", "Kotlin")
    )
)

val defaultProductName = "IC-2022.2"
val productName = System.getenv("PRODUCT_NAME") ?: defaultProductName
val maybeGithubRunNumber = System.getenv("GITHUB_RUN_NUMBER")?.toInt()
val descriptor = plugins.first { it.sdkVersion == productName }

// Import variables from gradle.properties file
val pluginGroup: String by project

// `pluginName_` variable ends with `_` because of the collision with Kotlin magic getter in the `intellij` closure.
// Read more about the issue: https://github.com/JetBrains/intellij-platform-plugin-template/issues/29
val pluginName_: String by project
val pluginVersion: String = pluginVersion(major = "2", minor = "2", patch = "0")
val pluginDescriptionFile: String by project
val pluginChangeNotesFile: String by project

val platformVersion: String by project
val packageVersion: String by project

group = pluginGroup
version = packageVersion

logger.lifecycle("Building Amazon Ion $pluginVersion for ${descriptor.platformType} ${descriptor.sdkVersion}")

dependencies {
    // Kotlin runtime dependency is provided by the IntelliJ platform.
}

intellij {
    pluginName.set(pluginName_)
    version.set(descriptor.sdkVersion)
    type.set(descriptor.platformType.acronym)
    downloadSources.set(true)
    updateSinceUntilBuild.set(false)

    // Plugin Dependencies -> https://www.jetbrains.org/intellij/sdk/docs/basics/plugin_structure/plugin_dependencies.html
    // Example: platformPlugins = com.intellij.java, com.jetbrains.php:203.4449.22
    plugins.set(descriptor.dependencies)
}

sourceSets {
    main {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("src/${descriptor.sourceFolder}/kotlin")
        }

        resources {
            srcDir("src/${descriptor.sourceFolder}/resources")
        }

        java {
            srcDirs("src/main/gen")
        }
    }
}

tasks {
    // Disable searchable options since it is failing during the build
    // Re-evaluate in the future if it starts to succeed.
    findByName("buildSearchableOptions")?.enabled = false

    compileKotlin {
        kotlinOptions {
            apiVersion = descriptor.kotlin.apiVersion
            jvmTarget = "11"
        }
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    buildPlugin {
        archiveClassifier.set(descriptor.sdkVersion)
    }

    patchPluginXml {
        version.set(pluginVersion)
        sinceBuild.set(descriptor.since)
        untilBuild.set(descriptor.until)

        pluginDescription.set(readResource(pluginDescriptionFile))
        changeNotes.set(readResource(pluginChangeNotesFile))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))

        // Publish to beta unless release is specified.
        if (System.getenv("PUBLISH_CHANNEL") != "release") {
            channels.set(listOf("beta"))
        }
    }
}

/**
 * Utility function to read a resource file.
 */
fun readResource(name: String) = file("resources/$name").readText()

/**
 * Function which creates a plugin version in SemVer format
 *
 * Examples:
 *
 * GIVEN (GitHub workflow environment):
 *  GITHUB_RUN_NUMBER: 30
 *  major: 2
 *  minor: 1
 *  patch: 1
 *  sdkVersion: IC-2022.2
 *
 * RETURNS:
 *  2.1.1+30-IC-2022.2
 *
 *
 * GIVEN (local dev environment):
 *  GITHUB_RUN_NUMBER: null
 *  major: 2
 *  minor: 2
 *  patch: 34
 *  sdkVersion: IC-2022.3
 *
 * RETURNS:
 *  2.2.34+0-IC-2022.3+alpha
 */
fun pluginVersion(major: String, minor: String, patch: String) =
    listOf(
        major,
        minor,
        maybeGithubRunNumber?.let { "$patch+$it-${descriptor.sdkVersion}" } ?: "$patch+0-${descriptor.sdkVersion}+alpha"
    ).joinToString(".")
