package com.amazon.ion.plugin.intellij

import com.intellij.lang.Language

class IonLanguage : Language("Ion") {
    companion object {
        val INSTANCE = IonLanguage()
    }
}
