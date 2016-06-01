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

package com.amazon.ion.plugin.intellij.helpers;


import org.apache.commons.codec.binary.Base64;

public class ContentCorrectnessHelper {

    /**
     * Validate Base64 value for BLOB data type
     *
     * @param text Input value
     * @return true if the value is a valid base64 encoded blob
     */
    public static boolean isValidBase64(CharSequence text) {
        final String input = text.toString();
        final String decoded = new String(Base64.decodeBase64(input));
        final String encoded = Base64.encodeBase64String(decoded.getBytes());
        return encoded.equals(input);
    }

}
