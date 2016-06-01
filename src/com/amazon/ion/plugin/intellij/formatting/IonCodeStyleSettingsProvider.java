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
import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.openapi.options.Configurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IonCodeStyleSettingsProvider extends CodeStyleSettingsProvider {

    @NotNull
    @Override
    public Configurable createSettingsPage(CodeStyleSettings settings, CodeStyleSettings originalSettings) {
        return new CodeStyleAbstractConfigurable(settings, originalSettings, "Simple") {
            @Override
            protected CodeStyleAbstractPanel createPanel(CodeStyleSettings settings) {
                return new IonCodeStyleMainPanel(getCurrentSettings(), settings);
            }

            @Nullable
            @Override
            public String getHelpTopic() {
                return null;
            }
        };
    }


    @Nullable
    @Override
    public String getConfigurableDisplayName() {
        return "Ion";
    }

    private static class IonCodeStyleMainPanel extends TabbedLanguageCodeStylePanel {

        @Override
        protected void initTabs(CodeStyleSettings settings) {
            addIndentOptionsTab(settings);
        }

        public IonCodeStyleMainPanel(CodeStyleSettings currentSettings, CodeStyleSettings settings) {
            super(IonLanguage.INSTANCE, currentSettings, settings);
        }
    }
}
