/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.catalina.ha;

import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

public interface ClusterSession extends Session, HttpSession {
   /**
    * returns true if this session is the primary session, if that is the
    * case, the manager can expire it upon timeout.
    * @return True if this session is primary
    */
   public boolean isPrimarySession();

   /**
    * Sets whether this is the primary session or not.
    * @param primarySession Flag value
    */
   public void setPrimarySession(boolean primarySession);


}
