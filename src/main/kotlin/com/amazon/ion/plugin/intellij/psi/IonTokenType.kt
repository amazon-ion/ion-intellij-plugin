package com.amazon.ion.plugin.intellij.psi

import com.amazon.ion.plugin.intellij.IonLanguage
import com.intellij.psi.tree.IElementType

class IonTokenType(debugName: String) : IElementType(debugName, IonLanguage.INSTANCE)
