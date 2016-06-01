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
import com.amazon.ion.plugin.intellij.psi.IonTypes;
import com.intellij.formatting.Alignment;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.WrapType;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Formatting model builder for ion.
 * NOTE: Incomplete and does not work
 */
public class IonFormattingModelBuilder implements FormattingModelBuilder {

    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {

        final com.amazon.ion.plugin.intellij.formatting.IonBlock rootBlock = new com.amazon.ion.plugin.intellij.formatting.IonBlock(element.getNode(),
                Wrap.createWrap(WrapType.NONE, false),
                Alignment.createAlignment(true),
                createSpaceBuilder(settings), settings);

        return FormattingModelProvider.createFormattingModelForPsiFile(
                element.getContainingFile(),
                rootBlock, settings);
    }

    private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {

        SpacingBuilder sb = new SpacingBuilder(settings, IonLanguage.INSTANCE);
        sb.after(IonTypes.COMMA).lineBreakInCode();
        sb.after(IonTypes.LBRACE).lineBreakInCode();
        sb.before(IonTypes.RBRACE).lineBreakInCode();
        sb.after(IonTypes.LBRACKET).lineBreakInCode();
        sb.before(IonTypes.RBRACKET).lineBreakInCode();
        sb.before(IonTypes.PAIR).lineBreakInCode();
        sb.around(IonTypes.COMMA).spacing(0, 1, 0, false, 0);
        sb.afterInside(IonTypes.KEY, IonTypes.OBJECT).lineBreakInCode();
        sb.around(IonTypes.SEPARATOR).spaces(1);
        return sb;
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
