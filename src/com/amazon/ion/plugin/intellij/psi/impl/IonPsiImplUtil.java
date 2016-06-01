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

package com.amazon.ion.plugin.intellij.psi.impl;

import com.amazon.ion.plugin.intellij.psi.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

public class IonPsiImplUtil {

    public static String getKeyName(IonKey keyElement) {
        ASTNode keyNode = keyElement.getNode().findChildByType(IonTypes.KEY_NAME);
        if (keyNode != null) {
            return keyNode.getText();
        } else {
            return null;
        }
    }

    public static String getValueAsString(IonValue val) {

        IElementType type = val.getFirstChild().getNode().getElementType();

        if (type == IonTypes.OBJECT) {
            return null;
        }

        return val.getText();
    }


    /**
     * Convenience method to return the actual element
     *
     * @param object
     * @return
     */
    public static PsiElement getPsiElement(IonObject object) {
        if (object.getStruct() != null) return object.getStruct();
        if (object.getExpression() != null) return object.getExpression();
        if (object.getArray() != null) return object.getArray();
        return null;
    }
}
