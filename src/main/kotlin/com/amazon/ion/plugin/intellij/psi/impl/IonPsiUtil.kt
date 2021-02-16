package com.amazon.ion.plugin.intellij.psi.impl

import com.amazon.ion.plugin.intellij.psi.IonKey
import com.amazon.ion.plugin.intellij.psi.IonObject
import com.amazon.ion.plugin.intellij.psi.IonTypes.KEY_NAME
import com.amazon.ion.plugin.intellij.psi.IonTypes.OBJECT
import com.amazon.ion.plugin.intellij.psi.IonValue
import com.intellij.psi.PsiElement

object IonPsiUtil {
    @JvmStatic
    fun getKeyName(keyElement: IonKey): String? = keyElement.node.findChildByType(KEY_NAME)?.text

    @JvmStatic
    fun getValueAsString(value: IonValue): String? =
        when (value.firstChild.node.elementType) {
            OBJECT -> null
            else -> value.text
        }

    @JvmStatic
    fun getPsiElement(obj: IonObject): PsiElement? = obj.struct ?: obj.expression ?: obj.array
}
