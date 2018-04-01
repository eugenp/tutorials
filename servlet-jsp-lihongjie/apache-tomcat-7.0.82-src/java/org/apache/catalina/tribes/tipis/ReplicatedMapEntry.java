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
package org.apache.catalina.tribes.tipis;

import java.io.IOException;
import java.io.Serializable;

/**
 * 
 * For smarter replication, an object can implement this interface to replicate diffs<br>
 * The replication logic will call the methods in the following order:<br>
 * <code>
 * 1. if ( entry.isDirty() ) <br>
 *      try {
 * 2.     entry.lock();<br>
 * 3.     byte[] diff = entry.getDiff();<br>
 * 4.     entry.reset();<br>
 *      } finally {<br>
 * 5.     entry.unlock();<br>
 *      }<br>
 *    }<br>
 * </code>
 * <br>
 * <br>
 * When the data is deserialized the logic is called in the following order<br>
 * <code>
 * 1. ReplicatedMapEntry entry = (ReplicatedMapEntry)objectIn.readObject();<br>
 * 2. if ( isBackup(entry)||isPrimary(entry) ) entry.setOwner(owner); <br>
 * </code>
 * <br>
 * 
 * 
 * @author Filip Hanik
 * @version 1.0
 */
public interface ReplicatedMapEntry extends Serializable {
    
    /**
     * Has the object changed since last replication
     * and is not in a locked state
     * @return boolean
     */
    public boolean isDirty();
    
    /**
     * If this returns true, the map will extract the diff using getDiff()
     * Otherwise it will serialize the entire object.
     * @return boolean
     */
    public boolean isDiffable();
    
    /**
     * Returns a diff and sets the dirty map to false
     * @return byte[]
     * @throws IOException
     */
    public byte[] getDiff() throws IOException;
    
    
    /**
     * Applies a diff to an existing object.
     * @param diff byte[]
     * @param offset int
     * @param length int
     * @throws IOException
     */
    public void applyDiff(byte[] diff, int offset, int length) throws IOException, ClassNotFoundException;
    
    /**
     * Resets the current diff state and resets the dirty flag
     */
    public void resetDiff();
    
    /**
     * Lock during serialization
     */
    public void lock();
    
    /**
     * Unlock after serialization
     */
    public void unlock();
    
    /**
     * This method is called after the object has been 
     * created on a remote map. On this method,
     * the object can initialize itself for any data that wasn't 
     * 
     * @param owner Object
     */
    public void setOwner(Object owner);
    
    /**
     * For accuracy checking, a serialized attribute can contain a version number
     * This number increases as modifications are made to the data.
     * The replicated map can use this to ensure accuracy on a periodic basis
     * @return long - the version number or -1 if the data is not versioned
     */
    public long getVersion();
    
    /**
     * Forces a certain version to a replicated map entry<br>
     * @param version long
     */
    public void setVersion(long version);

    /**
     * Return the last replicate time.
     */
    public long getLastTimeReplicated();

    /**
     * Set the last replicate time.
     * @param lastTimeReplicated
     */
    public void setLastTimeReplicated(long lastTimeReplicated);

    /**
     * If this returns true, to replicate that an object has been accessed
     * @return boolean
     */
    public boolean isAccessReplicate();

    /**
     * Access to an existing object.
     */
    public void accessEntry();

}