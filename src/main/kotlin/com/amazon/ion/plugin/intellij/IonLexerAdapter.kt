package com.amazon.ion.plugin.intellij

import com.intellij.lexer.FlexAdapter

class IonLexerAdapter : FlexAdapter(IonLexer(null))
