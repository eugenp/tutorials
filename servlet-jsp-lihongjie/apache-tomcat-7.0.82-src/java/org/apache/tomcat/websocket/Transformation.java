/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import javax.websocket.Extension;

/**
 * The internal representation of the transformation that a WebSocket extension
 * performs on a message.
 */
public interface Transformation {

    /**
     * Sets the next transformation in the pipeline.
     */
    void setNext(Transformation t);

    /**
     * Validate that the RSV bit(s) required by this transformation are not
     * being used by another extension. The implementation is expected to set
     * any bits it requires before passing the set of in-use bits to the next
     * transformation.
     *
     * @param i         The RSV bits marked as in use so far as an int in the
     *                  range zero to seven with RSV1 as the MSB and RSV3 as the
     *                  LSB
     *
     * @return <code>true</code> if the combination of RSV bits used by the
     *         transformations in the pipeline do not conflict otherwise
     *         <code>false</code>
     */
    boolean validateRsvBits(int i);

    /**
     * Obtain the extension that describes the information to be returned to the
     * client.
     */
    Extension getExtensionResponse();

    /**
     * Obtain more input data.
     *
     * @param opCode    The opcode for the frame currently being processed
     * @param fin       Is this the final frame in this WebSocket message?
     * @param rsv       The reserved bits for the frame currently being
     *                      processed
     * @param dest      The buffer in which the data is to be written
     */
    TransformationResult getMoreData(byte opCode, boolean fin, int rsv, ByteBuffer dest) throws IOException;

    /**
     * Validates the RSV and opcode combination (assumed to have been extracted
     * from a WebSocket Frame) for this extension. The implementation is
     * expected to unset any RSV bits it has validated before passing the
     * remaining RSV bits to the next transformation in the pipeline.
     *
     * @param rsv       The RSV bits received as an int in the range zero to
     *                  seven with RSV1 as the MSB and RSV3 as the LSB
     * @param opCode    The opCode received
     *
     * @return <code>true</code> if the RSV is valid otherwise
     *         <code>false</code>
     */
    boolean validateRsv(int rsv, byte opCode);

    /**
     * Takes the provided list of messages, transforms them, passes the
     * transformed list on to the next transformation (if any) and then returns
     * the resulting list of message parts after all of the transformations have
     * been applied.
     *
     * @param messageParts  The list of messages to be transformed
     *
     * @return  The list of messages after this any any subsequent
     *          transformations have been applied. The size of the returned list
     *          may be bigger or smaller than the size of the input list
     */
    List<MessagePart> sendMessagePart(List<MessagePart> messageParts);

    /**
     * Clean-up any resources that were used by the transformation.
     */
    void close();
}
