package com.amazon.ion.plugin.intellij

import com.intellij.openapi.util.IconLoader

/**
 * Common Icons used throughout the plugin.
 */
object IonIcons {
    /**
     * Alternative file type logos:
     * - Old nucleus looking logo - ion-file-type.ping
     * - Hex blue logo - ion-file-type-blue.png
     * - Orange logo - ion-file-type-orange.png
     * - Orange logo - ion-file-type-orange-gold.png
     */
    val FileType = icon("ion-file-type-orange.png")
    val Pluggable = icon("ion-plugin-pluggable.png")

    private fun icon(name: String) = IconLoader.getIcon("/META-INF/${name}", javaClass)
}
