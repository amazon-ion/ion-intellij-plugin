package com.amazon.ion.plugin.intellij.psi.impl

import com.amazon.ion.plugin.intellij.psi.IonContainer
import com.amazon.ion.plugin.intellij.psi.IonKey
import com.amazon.ion.plugin.intellij.psi.IonTypes.CONTAINER
import com.amazon.ion.plugin.intellij.psi.IonTypes.KEY_NAME
import com.amazon.ion.plugin.intellij.psi.IonValue
import com.intellij.psi.PsiElement

object IonPsiUtil {
    @JvmStatic
    fun getKeyName(keyElement: IonKey): String? = keyElement.node.findChildByType(KEY_NAME)?.text

    @JvmStatic
    fun getValueAsString(value: IonValue): String? =
        when (value.firstChild.node.elementType) {
            CONTAINER -> null
            else -> value.text
        }

    @JvmStatic
    fun getPsiElement(obj: IonContainer): PsiElement? = obj.struct ?: obj.sexpression ?: obj.list
}
