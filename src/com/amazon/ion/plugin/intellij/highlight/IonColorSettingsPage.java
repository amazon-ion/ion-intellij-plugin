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


import com.amazon.ion.plugin.intellij.IonIcons;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class IonColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Annotations", IonSyntaxHighlighter.annotation),
            new AttributesDescriptor("Key", IonSyntaxHighlighter.keyword),
            new AttributesDescriptor("Separator", IonSyntaxHighlighter.separator),
            new AttributesDescriptor("Numbers", IonSyntaxHighlighter.number),
            new AttributesDescriptor("Booleans", IonSyntaxHighlighter.bool),
            new AttributesDescriptor("Timestamps", IonSyntaxHighlighter.timestamp),
            new AttributesDescriptor("Blob Value", IonSyntaxHighlighter.blobValue),
            new AttributesDescriptor("Symbols", IonSyntaxHighlighter.symbol),
            new AttributesDescriptor("Strings", IonSyntaxHighlighter.ionString),
            new AttributesDescriptor("Comments", IonSyntaxHighlighter.comment),
            new AttributesDescriptor("Brackets", IonSyntaxHighlighter.brackets),
            new AttributesDescriptor("Braces", IonSyntaxHighlighter.braces),
            new AttributesDescriptor("LOB Braces", IonSyntaxHighlighter.lobBraces),
            new AttributesDescriptor("Parentheses", IonSyntaxHighlighter.parentheses),
            new AttributesDescriptor("Operators", IonSyntaxHighlighter.operators),
    };

    @Nullable
    @Override
    public Icon getIcon() {
        return IonIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new IonSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {

        String example = "// Demo text for ion colors\n" +
                "'Annotation'::{\n" +
                "    float_key:2.55, /* A comment for an awesome price */\n" +
                "    boolean_key: false,\n" +
                "    identifier_key:\"RDE-433-GYUTF\",\n" +
                "    integer_key:55,\n" +
                "    blob_key:{{ VG8gaW5maW5pdHkuLi4gYW5kIGJleW9uZCE= }},\n" +
                "    expression_key : (this is an expression),\n" +
                "    timestamp_key:2007-22-23T20:14:33Z,\n" +
                "    array_key:[\"Note A\", \"Note B\"],\n" +
                "    other_numbers:[0x10FBAD, 0b110101]\n" +
                "}";

        return example;
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Ion";
    }
}
