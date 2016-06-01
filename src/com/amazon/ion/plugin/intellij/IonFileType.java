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
package com.amazon.ion.plugin.intellij;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 */
public class IonFileType extends LanguageFileType {
    public static final IonFileType INSTANCE = new IonFileType();

    private IonFileType() {
        super(IonLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Ion file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Ion language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "ion";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return IonIcons.FILE;
    }
}
