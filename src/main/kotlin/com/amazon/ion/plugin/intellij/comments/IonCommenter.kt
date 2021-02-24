package com.amazon.ion.plugin.intellij.comments

import com.intellij.lang.Commenter

/**
 * Configures some of the shortcuts to automatically comment content in an Ion file.
 */
class IonCommenter : Commenter {

    override fun getLineCommentPrefix(): String = "//"

    override fun getBlockCommentPrefix(): String = "/*"

    override fun getBlockCommentSuffix(): String = "*/"

    override fun getCommentedBlockCommentPrefix(): String? = null

    override fun getCommentedBlockCommentSuffix(): String? = null
}
