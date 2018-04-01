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

package org.apache.catalina.ha.deploy;

import org.apache.catalina.ha.ClusterMessageBase;
import org.apache.catalina.tribes.Member;

/**
 * Contains the data for a file being transferred over TCP, this is 
 * essentially a fragment of a file, read and written by the FileMessageFactory
 * @author Filip Hanik
 * @version 1.0
 */

public class FileMessage extends ClusterMessageBase {
    private static final long serialVersionUID = 1L;

    private int messageNumber;
    private byte[] data;
    private int dataLength;
    
    private long totalLength;
    private long totalNrOfMsgs;
    private String fileName;
    private String contextName;
    
    public FileMessage(Member source,
                       String fileName,
                       String contextName) {
        this.address=source;
        this.fileName=fileName;
        this.contextName=contextName;
    }
    
    /*
    public void writeExternal(ObjectOutput out) throws IOException {
                   
    }
    
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
                  
    }
    */
   
    public int getMessageNumber() {
        return messageNumber;
    }
    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }
    public long getTotalNrOfMsgs() {
        return totalNrOfMsgs;
    }
    public void setTotalNrOfMsgs(long totalNrOfMsgs) {
        this.totalNrOfMsgs = totalNrOfMsgs;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data, int length) {
        this.data = data;
        this.dataLength = length;
    }
    public int getDataLength() {
        return dataLength;
    }
    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }
    public long getTotalLength() {
        return totalLength;
    }
    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    @Override
    public String getUniqueId() {
        StringBuilder result = new StringBuilder(getFileName());
        result.append("#-#");
        result.append(getMessageNumber());
        result.append("#-#");
        result.append(System.currentTimeMillis());
        return result.toString();
    }

    
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getContextName() {
        return contextName;
    }
    
}
