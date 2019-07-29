package io.sirix.tutorial.json;

/**
 * Copyright (c) 2011, University of Konstanz, Distributed Systems Group All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met: * Redistributions of source code must retain the
 * above copyright notice, this list of conditions and the following disclaimer. * Redistributions
 * in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 * * Neither the name of the University of Konstanz nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.sirix.access.trx.node.json.objectvalue.ArrayValue;
import org.sirix.access.trx.node.json.objectvalue.BooleanValue;
import org.sirix.access.trx.node.json.objectvalue.ObjectValue;
import org.sirix.access.trx.node.json.objectvalue.StringValue;
import org.sirix.api.json.JsonNodeTrx;
import org.sirix.exception.SirixException;

/**
 * <h1>JSON-Document</h1>
 *
 * <p>
 * This class creates a JSON document.
 * </p>
 *
 * <code><pre>
 * {
 *   "foo": ["bar", null, 2.33],
 *   "bar": { "hello": "world", "helloo": true },
 *   "baz": "hello",
 *   "tada": [{"foo":"bar"},{"baz":false},"boo",{},[]]
 * }
 * </pre></code>
 */
public final class JsonDocumentCreator {

    public static final String JSON = "{\"foo\":[\"bar\",null,2.33],\"bar\":{\"hello\":\"world\",\"helloo\":true},\"baz\":\"hello\",\"tada\":[{\"foo\":\"bar\"},{\"baz\":false},\"boo\",{},[]]}";

    /**
     * Private Constructor, not used.
     */
    private JsonDocumentCreator() {
        throw new AssertionError("Not permitted to call constructor!");
    }

    /**
     * Create simple test document containing all supported node kinds.
     *
     * @param wtx {@link JsonNodeWriteTrx} to write to
     * @throws SirixException if anything weird happens
     */
    public static void create(final JsonNodeTrx wtx) {
        wtx.insertObjectAsFirstChild();

        wtx.insertObjectRecordAsFirstChild("foo", new ArrayValue())
           .insertStringValueAsFirstChild("bar")
           .insertNullValueAsRightSibling()
           .insertNumberValueAsRightSibling(2.33);

        wtx.moveToParent().trx().moveToParent();

        wtx.insertObjectRecordAsRightSibling("bar", new ObjectValue())
           .insertObjectRecordAsFirstChild("hello", new StringValue("world"))
           .moveToParent();
        wtx.insertObjectRecordAsRightSibling("helloo", new BooleanValue(true))
           .moveToParent().trx().moveToParent().trx().moveToParent();

        wtx.insertObjectRecordAsRightSibling("baz", new StringValue("hello"))
           .moveToParent();

        wtx.insertObjectRecordAsRightSibling("tada", new ArrayValue())
           .insertObjectAsFirstChild()
           .insertObjectRecordAsFirstChild("foo", new StringValue("bar"))
           .moveToParent().trx().moveToParent();

        wtx.insertObjectAsRightSibling()
           .insertObjectRecordAsFirstChild("baz", new BooleanValue(false))
           .moveToParent().trx().moveToParent();

        wtx.insertStringValueAsRightSibling("boo")
           .insertObjectAsRightSibling()
           .insertArrayAsRightSibling();

        wtx.moveToDocumentRoot();
    }
}
