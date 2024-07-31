package io.sirix.tutorial.json;

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

    public static void createVersioned(final JsonNodeTrx wtx) {
        // Create sample document.
        JsonDocumentCreator.create(wtx);
        wtx.commit();

        // Add changes and commit a second revision.
        wtx.moveToDocumentRoot().trx().moveToFirstChild();
        wtx.insertObjectRecordAsFirstChild("revision2", new StringValue("yes"));
        wtx.commit();

        // Add changes and commit a third revision.
        wtx.moveToDocumentRoot().trx().moveToFirstChild().trx().moveToFirstChild();
        wtx.insertObjectRecordAsRightSibling("revision3", new StringValue("yes"));
        wtx.commit();
    }
}
