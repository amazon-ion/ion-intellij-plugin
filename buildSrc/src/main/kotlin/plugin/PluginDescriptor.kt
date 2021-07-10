package plugin

data class PluginDescriptor(
    val since: String,
    val until: String,
    val platformType: PlatformType,
    val sdkVersion: String,
    val dependencies: List<String>
)

enum class PlatformType(val acronym: String) {
    IdeaCommunity("IC"),
    IdeaUltimate("IU"),
    RubyMine("RM"),
    PyCharm("PY")
}
