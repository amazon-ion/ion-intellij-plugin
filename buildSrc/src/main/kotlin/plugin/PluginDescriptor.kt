package plugin

data class PluginDescriptor(
    val since: String,
    val until: String,
    val platformType: PlatformType,
    val sdkVersion: String,
    val sourceFolder: String,
    val kotlin: KotlinOptions,
    val dependencies: List<String>
) {
    data class KotlinOptions(
        val apiVersion: String
    )
}

enum class PlatformType(val acronym: String) {
    IdeaCommunity("IC"),
    IdeaUltimate("IU"),
    RubyMine("RM"),
    PyCharm("PY")
}
