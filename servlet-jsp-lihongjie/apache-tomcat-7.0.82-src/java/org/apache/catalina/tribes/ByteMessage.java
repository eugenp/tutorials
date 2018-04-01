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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * A byte message is not serialized and deserialized by the channel
 * instead it is sent as a byte array<br>
 * By default Tribes uses java serialization when it receives an object
 * to be sent over the wire. Java serialization is not the most
 * efficient of serializing data, and Tribes might not even
 * have access to the correct class loaders to deserialize the object properly.
 * <br>
 * The ByteMessage class is a class where the channel when it receives it will
 * not attempt to perform serialization, instead it will simply stream the <code>getMessage()</code>
 * bytes.<br>
 * If you are using multiple applications on top of Tribes you should add some sort of header
 * so that you can decide with the <code>ChannelListener.accept()</code> whether this message was intended
 * for you.
 * @author Filip Hanik
 */
public class ByteMessage implements Externalizable {
    /**
     * Storage for the message to be sent
     */
    private byte[] message;


    /**
     * Creates an empty byte message
     * Constructor also for deserialization
     */
    public ByteMessage() {
    }

    /**
     * Creates a byte message wit h
     * @param data byte[] - the message contents
     */
    public ByteMessage(byte[] data) {
        message = data;
    }

    /**
     * Returns the message contents of this byte message
     * @return byte[] - message contents, can be null
     */
    public byte[] getMessage() {
        return message;
    }

    /**
     * Sets the message contents of this byte message
     * @param message byte[]
     */
    public void setMessage(byte[] message) {
        this.message = message;
    }

    /**
     * @see java.io.Externalizable#readExternal
     * @param in ObjectInput
     * @throws IOException
     */
    @Override
    public void readExternal(ObjectInput in ) throws IOException {
        int length = in.readInt();
        message = new byte[length];
        in.readFully(message);
    }

    /**
     * @see java.io.Externalizable#writeExternal
     * @param out ObjectOutput
     * @throws IOException
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(message!=null?message.length:0);
        if ( message!=null ) out.write(message,0,message.length);
    }

}
