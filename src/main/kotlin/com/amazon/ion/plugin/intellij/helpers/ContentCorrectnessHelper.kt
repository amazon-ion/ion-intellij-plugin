package com.amazon.ion.plugin.intellij.helpers

import org.apache.commons.codec.binary.Base64

object ContentCorrectnessHelper {

    /**
     * Validates that a sequence of character is valid Base64. This is in order
     * to validate Ion Blobs.
     *
     * Should not return an exception, only true or false if the content is invalid.
     */
    @JvmStatic
    fun isValidBase64(text: CharSequence) = runCatching {
        text.toString() ==
        String(
            Base64.decodeBase64(text.toString())
        ).let { Base64.encodeBase64String(it.toByteArray()) }
    }.getOrElse { false }

}
