import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease
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
    id("org.jetbrains.intellij.platform") version "2.0.1"
}

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
  }
}

val plugins = listOf(
    PluginDescriptor(
        since = "222",
        until = "223.*",
        platformVersion = "2022.2",
        platformType = PlatformType.IdeaCommunity,
        sourceFolder = "IC-222",
        kotlin = KotlinOptions(
            apiVersion = "1.6"
        ),
        bundledDependencies = listOf("com.intellij.java", "org.jetbrains.kotlin")
    ),
    PluginDescriptor(
        since = "231",
        until = "231.*",
        platformVersion = "2023.1",
        platformType = PlatformType.IdeaCommunity,
        sourceFolder = "IC-231",
        kotlin = KotlinOptions(
            apiVersion = "1.6"
        ),
        bundledDependencies = listOf("com.intellij.java", "org.jetbrains.kotlin")
    ),
    PluginDescriptor(
            since = "232",
            until = "232.*",
            platformVersion = "2023.2",
            platformType = PlatformType.IdeaCommunity,
            sourceFolder = "IC-232",
            kotlin = KotlinOptions(
                    apiVersion = "1.6"
            ),
            bundledDependencies = listOf("com.intellij.java", "org.jetbrains.kotlin")
    ),
    PluginDescriptor(
            since = "233",
            until = "233.*",
            platformVersion = "2023.3",
            platformType = PlatformType.IdeaCommunity,
            sourceFolder = "IC-233",
            kotlin = KotlinOptions(
                    apiVersion = "1.6"
            ),
            bundledDependencies = listOf("com.intellij.java", "org.jetbrains.kotlin")
    ),
    PluginDescriptor(
        since = "241",
        until = "241.*",
        platformVersion = "2024.1",
        platformType = PlatformType.IdeaCommunity,
        sourceFolder = "IC-241",
        kotlin = KotlinOptions(
            apiVersion = "1.6"
        ),
        bundledDependencies = listOf("com.intellij.java", "org.jetbrains.kotlin")
    )
)

val defaultProductName = "2024.1"
val productName = System.getenv("PRODUCT_NAME") ?: defaultProductName
val maybeGithubRunNumber = System.getenv("GITHUB_RUN_NUMBER")?.toInt()
val descriptor = plugins.first { it.platformVersion == productName }

// Import variables from gradle.properties file
val pluginGroup: String by project

// `pluginName_` variable ends with `_` because of the collision with Kotlin magic getter in the `intellij` closure.
// Read more about the issue: https://github.com/JetBrains/intellij-platform-plugin-template/issues/29
val pluginName_: String by project
val pluginVersion: String = pluginVersion(major = "2", minor = "7", patch = "1")
val pluginDescriptionFile: String by project
val pluginChangeNotesFile: String by project

val platformVersion: String by project
val packageVersion: String by project

group = pluginGroup
version = packageVersion

logger.lifecycle("Building Amazon Ion $pluginVersion for ${descriptor.platformType} ${descriptor.platformVersion}")

dependencies {
    intellijPlatform {
        create(descriptor.platformType.acronym, descriptor.platformVersion) // https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html#setting-up-intellij-platform
        bundledPlugins(descriptor.bundledDependencies)
        pluginVerifier()
        instrumentationTools()
    }
}

intellijPlatform {
    pluginConfiguration {
        name.set(pluginName_)
        version.set(descriptor.platformVersion)
        description.set(readResource(pluginDescriptionFile))
        changeNotes.set(readResource(pluginChangeNotesFile))

        ideaVersion {
            sinceBuild.set(descriptor.since)
            untilBuild.set(descriptor.until)
        }
    }

    publishing {
        token.set(System.getenv("PUBLISH_TOKEN"))

        // Publish to beta unless release is specified.
        if (System.getenv("PUBLISH_CHANNEL") != "release") {
            channels.set(listOf("beta"))
        }
    }

    pluginVerification {
        ides {
            ide(IntelliJPlatformType.IntellijIdeaCommunity, descriptor.platformVersion)
            recommended()
            select {
                types.set(listOf(IntelliJPlatformType.IntellijIdeaCommunity))
                channels.set(listOf(ProductRelease.Channel.BETA))
                sinceBuild.set(descriptor.since)
                untilBuild.set(descriptor.until)
            }
        }
    }
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
            jvmTarget = "17"
        }
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    withType<JavaCompile> {
        options.release.set(17)
    }

    buildPlugin {
        archiveClassifier.set(descriptor.getSDKVersion())
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
 *  sdkVersion: 2022.2
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
 *  sdkVersion: 2022.3
 *
 * RETURNS:
 *  2.2.34+0-IC-2022.3+alpha
 */
fun pluginVersion(major: String, minor: String, patch: String) =
    listOf(
        major,
        minor,
        maybeGithubRunNumber?.let { "$patch+$it-${descriptor.getSDKVersion()}" }
            ?: "$patch+0-${descriptor.getSDKVersion()}+alpha"
    ).joinToString(".")
