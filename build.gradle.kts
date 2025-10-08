import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    // > "Using Kotlin 2.x is recommended for plugins targeting 2024.3 or later and required for 2025.1 or later."
    // https://plugins.jetbrains.com/docs/intellij/using-kotlin.html#adding-kotlin-support
    kotlin("jvm") version "2.2.20"
    // IDE selection for pluginVerification is broken in IntelliJ Platform Gradle Plugin 2.9.0
    // select {} and recommended() don't work, only ide() seems to.
    // See https://github.com/JetBrains/intellij-platform-gradle-plugin/issues/2025
    // For now just stay on 2.8.0.
    id("org.jetbrains.intellij.platform") version "2.8.0"
}

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()
    gradlePluginPortal()

    intellijPlatform {
        defaultRepositories()
        jetbrainsRuntime()
    }
}

val maybeGithubRunNumber = System.getenv("GITHUB_RUN_NUMBER")?.toInt()

// Import variables from gradle.properties file
val pluginGroup: String by project

// `pluginName_` variable ends with `_` because of the collision with Kotlin magic getter in the `intellij` closure.
// Read more about the issue: https://github.com/JetBrains/intellij-platform-plugin-template/issues/29
val pluginName_: String by project
val pluginVersion: String = pluginVersion(major = "2", minor = "7", patch = "0")
val supportedSinceIdeVersion: String by project
val supportedUntilIdeVersion: String by project
val pluginDescriptionFile: String by project
val pluginChangeNotesFile: String by project

val isCI: Boolean = System.getenv("CI") == "true"

group = pluginGroup
version = pluginVersion

logger.lifecycle("Building Amazon Ion $pluginVersion")

dependencies {
    intellijPlatform {
        // This should be equivalent to the lowest version that we build against.
        intellijIdeaUltimate(supportedSinceIdeVersion.let(::branchToProductReleaseVersion))

        bundledPlugins(listOf(
            "com.intellij.java",
            "org.jetbrains.kotlin",
        ))
        pluginVerifier()

        testFramework(TestFrameworkType.Platform)
    }

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    // Set minimum version (Gradle may resolve to a higher patch version) because v4.13.0 is affected by CVE-2020-15250
    testCompileOnly("junit:junit:4.13.1")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
}

intellijPlatform {
    pluginConfiguration {
        name.set(pluginName_)
        version.set(pluginVersion)
        description.set(readResource(pluginDescriptionFile))
        changeNotes.set(readResource(pluginChangeNotesFile))

        ideaVersion {
            sinceBuild.set(supportedSinceIdeVersion)
            untilBuild.set(supportedUntilIdeVersion)
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
            select {
                types.set(listOf(IntelliJPlatformType.IntellijIdeaUltimate))
                channels.set(
                    listOf(
                        ProductRelease.Channel.RELEASE,
                        ProductRelease.Channel.EAP,
                    )
                )
                sinceBuild.set(supportedSinceIdeVersion)
                untilBuild.set(supportedUntilIdeVersion)
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs("src/main/gen")
        }
    }
}

// We were seeing slow builds similar to https://github.com/gradle/gradle/issues/26006
// Disabling incremental build snapshots dropped GHA :compileJava time from >5m to ~3m.
// Caching in GHA might make all this better.
tasks.withType<JavaCompile> {
    if (isCI) options.setIncremental(false)
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
 *
 * RETURNS:
 *  2.1.1+30
 *
 *
 * GIVEN (local dev environment):
 *  GITHUB_RUN_NUMBER: null
 *  major: 2
 *  minor: 2
 *  patch: 34
 *
 * RETURNS:
 *  2.2.34+0+alpha
 */
fun pluginVersion(major: String, minor: String, patch: String) =
    listOf(
        major,
        minor,
        maybeGithubRunNumber?.let { "$patch+$it" } ?: "$patch+0+alpha"
    ).joinToString(".")

/**
 * Converts the 3-digit "branch" to a product RELEASE version.
 * E.g.:
 * "241" -> "2024.1"
 */
fun branchToProductReleaseVersion(branch: String): String {
    val year = branch.slice(0..1)
    val minor = branch[2]
    return "20$year.$minor"
}
