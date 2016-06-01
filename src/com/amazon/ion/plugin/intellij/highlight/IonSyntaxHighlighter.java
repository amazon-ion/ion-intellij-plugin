/*
 * Copyright 2015-[2016] Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is located at
 *
 *     http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package com.amazon.ion.plugin.intellij.highlight;

import com.amazon.ion.plugin.intellij.IonLexer;
import com.amazon.ion.plugin.intellij.psi.IonTypes;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.Reader;

import static com.amazon.ion.plugin.intellij.psi.IonTypes.*;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class IonSyntaxHighlighter extends SyntaxHighlighterBase {


    static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("SIMPLE_BAD_CHARACTER",
            new TextAttributes(JBColor.RED, null, null, null, Font.BOLD));

    static final TextAttributesKey timestamp = TextAttributesKey.createTextAttributesKey("TIMESTAMP");
    static final TextAttributesKey blobValue = TextAttributesKey.createTextAttributesKey("ION_BLOB");
    static final TextAttributesKey lobBraces = TextAttributesKey.createTextAttributesKey("ION_LOB_BRACES");
    static final TextAttributesKey ionString = TextAttributesKey.createTextAttributesKey("ION_STRING");
    static final TextAttributesKey symbol = TextAttributesKey.createTextAttributesKey("ION_SYMBOL");
    static final TextAttributesKey operators = TextAttributesKey.createTextAttributesKey("ION_OPERATORS");
    static final TextAttributesKey separator = DefaultLanguageHighlighterColors.OPERATION_SIGN;
    static final TextAttributesKey keyword = DefaultLanguageHighlighterColors.KEYWORD;
    static final TextAttributesKey number = DefaultLanguageHighlighterColors.NUMBER;
    static final TextAttributesKey bool = DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL;
    static final TextAttributesKey comment = DefaultLanguageHighlighterColors.BLOCK_COMMENT;
    static final TextAttributesKey brackets = DefaultLanguageHighlighterColors.BRACKETS;
    static final TextAttributesKey braces = DefaultLanguageHighlighterColors.BRACES;
    static final TextAttributesKey annotation = DefaultLanguageHighlighterColors.CLASS_NAME;
    static final TextAttributesKey parentheses = DefaultLanguageHighlighterColors.PARENTHESES;


    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{separator};
    private static final TextAttributesKey[] KEY_NAME = new TextAttributesKey[]{keyword};
    private static final TextAttributesKey[] NUMBERS = new TextAttributesKey[]{number};
    private static final TextAttributesKey[] BOOLEANS = new TextAttributesKey[]{bool};
    private static final TextAttributesKey[] ION_SYMBOL = new TextAttributesKey[]{symbol};
    private static final TextAttributesKey[] STRING = new TextAttributesKey[]{ionString};
    private static final TextAttributesKey[] COMMENTS = new TextAttributesKey[]{comment};
    private static final TextAttributesKey[] BRACKETS = new TextAttributesKey[]{brackets};
    private static final TextAttributesKey[] BRACES = new TextAttributesKey[]{braces};
    private static final TextAttributesKey[] ANNOTATION = new TextAttributesKey[]{annotation};
    private static final TextAttributesKey[] PARENTHESES = new TextAttributesKey[]{parentheses};
    private static final TextAttributesKey[] TIMESTAMPS = new TextAttributesKey[]{timestamp};
    private static final TextAttributesKey[] BLOB_VALUE = new TextAttributesKey[]{blobValue};
    private static final TextAttributesKey[] LOB_BRACES = new TextAttributesKey[]{lobBraces};
    private static final TextAttributesKey[] OPERATORS = new TextAttributesKey[]{operators};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];


    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new FlexAdapter(new IonLexer((Reader) null));
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {

        if (tokenType.equals(SEPARATOR)) {
            return SEPARATOR_KEYS;
        } else if (tokenType.equals(IonTypes.KEY_NAME)) {
            return KEY_NAME;
        } else if (tokenType.equals(INTEGER) || tokenType.equals(DECIMAL) || tokenType.equals(HEXINT) || tokenType.equals(BININT)) {
            return NUMBERS;
        } else if (tokenType.equals(BOOLEAN) || tokenType.equals(NULL)) {
            return BOOLEANS;
        } else if (tokenType.equals(Q_VALUE) || tokenType.equals(IDENTIFIER)) {
            return ION_SYMBOL;
        } else if (tokenType.equals(QQ_VALUE) || tokenType.equals(QQQ_VALUE)) {
            return STRING;
        } else if (tokenType.equals(LBRACE) || tokenType.equals(RBRACE)) {
            return BRACES;
        } else if (tokenType.equals(LBRACKET) || tokenType.equals(RBRACKET)) {
            return BRACKETS;
        } else if (tokenType.equals(LPAREN) || tokenType.equals(RPAREN)) {
            return PARENTHESES;
        } else if (tokenType.equals(COMMENT)) {
            return COMMENTS;
        } else if (tokenType.equals(IonTypes.ANNOTATION)) {
            return ANNOTATION;
        } else if (tokenType.equals(TIMESTAMP)) {
            return TIMESTAMPS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else if (tokenType.equals(IonTypes.BLOB_VALUE)) {
            return BLOB_VALUE;
        } else if (tokenType.equals(LOB_START) || tokenType.equals(LOB_END)) {
            return LOB_BRACES;
        } else if (tokenType.equals(OPERATOR)) {
            return OPERATORS;
        } else {
            return EMPTY_KEYS;
        }

    }
}
