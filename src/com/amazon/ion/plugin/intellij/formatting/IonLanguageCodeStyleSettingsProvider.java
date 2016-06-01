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

package com.amazon.ion.plugin.intellij.formatting;

import com.amazon.ion.plugin.intellij.IonLanguage;
import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IonLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {

    @Nullable
    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        return new IndentOptionsEditor();
    }

    @NotNull
    @Override
    public Language getLanguage() {
        return IonLanguage.INSTANCE;
    }

    @Override
    public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer, @NotNull SettingsType settingsType) {
        if (settingsType == SettingsType.INDENT_SETTINGS) {
            consumer.showAllStandardOptions();
        }
    }

    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        return "// Demo text for ion colors\n" +
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
    }
}
