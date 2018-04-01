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

package org.apache.catalina.tribes.membership;

import java.io.IOException;

import org.apache.catalina.tribes.util.Arrays;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class StaticMember extends MemberImpl {
    public StaticMember() {
        super();
    }

    public StaticMember(String host, int port, long aliveTime) throws IOException {
        super(host, port, aliveTime);
    }

    public StaticMember(String host, int port, long aliveTime, byte[] payload) throws IOException {
        super(host, port, aliveTime, payload);
    }
     
    /**
     * @param host String, either in byte array string format, like {214,116,1,3}
     * or as a regular hostname, 127.0.0.1 or tomcat01.mydomain.com
     */
    public void setHost(String host) {
        if ( host == null ) return;
        if ( host.startsWith("{") ) setHost(Arrays.fromString(host));
        else try { setHostname(host); }catch (IOException x) { throw new RuntimeException(x);}
        
    }
    
    /**
     * @param domain String, either in byte array string format, like {214,116,1,3}
     * or as a regular string value like 'mydomain'. The latter will be converted using ISO-8859-1 encoding
     */
    public void setDomain(String domain) {
        if ( domain == null ) return;
        if ( domain.startsWith("{") ) setDomain(Arrays.fromString(domain));
        else setDomain(Arrays.convert(domain));
    }
    
    /**
     * @param id String, must be in byte array string format, like {214,116,1,3} and exactly 16 bytes long
     */
    public void setUniqueId(String id) {
        byte[] uuid = Arrays.fromString(id);
        if ( uuid==null || uuid.length != 16 ) throw new RuntimeException("UUID must be exactly 16 bytes, not:"+id);
        setUniqueId(uuid);
    }
    
    
}