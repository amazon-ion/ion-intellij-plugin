package plugin

/**
 * Defines the parameters needed to create a distribution of the plugin.
 *
 * For more information about how to specify which products the plugin can be compatible for and
 * how to specify compatibility {@see https://plugins.jetbrains.com/docs/intellij/build-number-ranges.html}.
 */
data class PluginDescriptor(
    /**
     * Earliest version string this distribution would be compatible with.
     */
    val since: String,

    /**
     * Latest version string this distribution would be compatible with. Wildcards supported, i.e. 202.*
     */
    val until: String,

    /**
     * The platform that this distribution will run on.
     */
    val platformType: PlatformType,

    /**
     * The Platform SDK Version that this distribution will run on.
     */
    val sdkVersion: String,

    /**
     * Source folder which defines source code specific to this distribution.
     */
    val sourceFolder: String,

    /**
     * Options specific to the Kotlin runtime.
     */
    val kotlin: KotlinOptions,

    /**
     * Plugin dependencies. {@see https://plugins.jetbrains.com/docs/intellij/plugin-dependencies.html}
     */
    val dependencies: List<String>
) {
    data class KotlinOptions(
        /**
         * API Version to limit the Kotlin Compiler to when compiling the plugin. Ensures runtime compatibility
         * with older IDEs that run on an older Kotlin runtime than the plugin.
         */
        val apiVersion: String
    )
}

enum class PlatformType(val acronym: String) {
    IdeaCommunity("IC")
}
