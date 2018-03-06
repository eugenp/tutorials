/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package jsp2.examples.el;

import java.util.Locale;

/**
 * Defines the functions for the jsp2 example tag library.
 *
 * <p>Each function is defined as a static method.</p>
 */
public class Functions {
    public static String reverse( String text ) {
        return new StringBuilder( text ).reverse().toString();
    }

    public static int numVowels( String text ) {
        String vowels = "aeiouAEIOU";
        int result = 0;
        for( int i = 0; i < text.length(); i++ ) {
            if( vowels.indexOf( text.charAt( i ) ) != -1 ) {
                result++;
            }
        }
        return result;
    }

    public static String caps( String text ) {
        return text.toUpperCase(Locale.ENGLISH);
    }
}
