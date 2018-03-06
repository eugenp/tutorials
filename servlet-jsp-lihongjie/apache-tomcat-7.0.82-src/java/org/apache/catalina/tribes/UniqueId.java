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
package org.apache.catalina.tribes;

import java.io.Serializable;

import org.apache.catalina.tribes.util.Arrays;

/**
 * <p>Title: Represents a globally unique Id</p>
 *
 * <p>Company: </p>
 *
 * @author Filip Hanik
 * @version 1.0
 */
public final class UniqueId implements Serializable{
    private static final long serialVersionUID = 1L;

    byte[] id;
    
    public UniqueId() {
    }

    public UniqueId(byte[] id) {
        this.id = id;
    }
    
    public UniqueId(byte[] id, int offset, int length) {
        this.id = new byte[length];
        System.arraycopy(id,offset,this.id,0,length);
    }
    
    @Override
    public int hashCode() {
        if ( id == null ) return 0;
        return Arrays.hashCode(id);
    }
    
    @Override
    public boolean equals(Object other) {
        boolean result = (other instanceof UniqueId);
        if ( result ) {
            UniqueId uid = (UniqueId)other;
            if ( this.id == null && uid.id == null ) result = true;
            else if ( this.id == null && uid.id != null ) result = false;
            else if ( this.id != null && uid.id == null ) result = false;
            else result = Arrays.equals(this.id,uid.id);
        }//end if
        return result;
    }
    
    public byte[] getBytes() {
        return id;
    }
    
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("UniqueId");
        buf.append(Arrays.toString(id));
        return buf.toString();
    }

}