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

package javax.servlet.jsp.el;

/**
 * <p>The interface to a map between EL function names and methods.</p>
 *
 * <p>Classes implementing this interface may, for instance, consult tag library
 * information to resolve the map. </p>
 *
 * @since 2.0
 * @deprecated As of JSP 2.1, replaced by javax.el.FunctionMapper
 */
@SuppressWarnings("dep-ann") // TCK signature test fails with annotation
public interface FunctionMapper {
  /**
   * Resolves the specified local name and prefix into a Java.lang.Method.
   * Returns null if the prefix and local name are not found.
   * 
   * @param prefix the prefix of the function, or "" if no prefix.
   * @param localName the short name of the function
   * @return the result of the method mapping.  Null means no entry found.
   **/
  public java.lang.reflect.Method resolveFunction(String prefix, 
      String localName);
}
