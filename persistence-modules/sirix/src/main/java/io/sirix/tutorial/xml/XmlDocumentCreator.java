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

package io.sirix.tutorial.xml;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.brackit.xquery.atomic.QNm;
import org.sirix.api.Database;
import org.sirix.api.xml.XmlNodeTrx;
import org.sirix.api.xml.XmlResourceManager;
import org.sirix.exception.SirixException;
import org.sirix.service.xml.shredder.InsertPosition;
import org.sirix.service.xml.shredder.XmlShredder;

/**
 * <h1>XmlDocumentCreator</h1>
 *
 * <p>
 * This class creates an XML document that contains all features seen in the Extensible Markup
 * Language (XML) 1.1 (Second Edition) as well as the Namespaces in XML 1.1 (Second Edition).
 * </p>
 *
 * <p>
 * The following figure describes the created test document (see <code>xml/test.xml</code>). The
 * nodes are described as follows:
 *
 * <ul>
 * <li><code>Kind.ROOT: doc()</code></li>
 * <li><code>Kind.ELEMENT : &lt;prefix:localPart&gt;</code></li>
 * <li><code>Kind.NAMESPACE: §prefix:namespaceURI</code></li>
 * <li><code>Kind.ATTRIBUTE: &#64;prefix:localPart='value'</code></li>
 * <li><code>Kind.TEXT: #value</code></li>
 * <li><code>Kind.COMMENT: %comment</code></li>
 * <li><code>Kind.PI: &amp;content:target</code></li>
 * </ul>
 *
 * without processing instruction and comment:
 *
 * <pre>
 * 0 doc()
 * |-  1 &lt;p:a §p:ns @i='j'&gt;
 *     |-  4 #oops1
 *     |-  5 &lt;b&gt;
 *     |   |-  6 #foo
 *     |   |-  7 &lt;c/&gt;
 *     |-  8 #oops2
 *     |-  9 &lt;b @p:x='y'&gt;
 *     |   |- 11 &lt;c/&gt;
 *     |   |- 12 #bar
 *     |- 13 #oops3
 * </pre>
 *
 * with processing instruction and comment:
 *
 * <pre>
 * 0 doc()
 * |-  1 &lt;p:a §p:ns @i='j'&gt;
 *     |-  4 %foo
 *     |-  5 #oops1
 *     |-  6 &lt;b&gt;
 *     |   |-  7 #foo
 *     |   |-  8 &lt;c/&gt;
 *     |-  9 &amp;bar:baz=\"foo\"
 *     |-  10 #oops2
 *     |-  11 &lt;b @p:x='y'&gt;
 *     |   |- 13 &lt;c/&gt;
 *     |   |- 14 #bar
 *     |- 15 #oops3
 * </pre>
 *
 * </p>
 */
public final class XmlDocumentCreator {

  /** String representation of revisioned xml file. */
  public static final String REVXML =
      "<article><title>A Test Document</title><para>This is para 1.</para><para>This is para 2<emphasis>"
          + "with emphasis</emphasis>in it.</para><para>This is para 3.</para><para id=\"p4\">This is "
          + "para 4.</para><para id=\"p5\">This is para 5.</para><para>This is para 6."
          + "</para><para>This is para 7.</para><para>This is para 8.</para><para>This is para 9."
          + "</para></article>";

  /** String representation of ID. */
  public static final String ID = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><p:a xmlns:p=\"ns\" "
      + "id=\"1\" i=\"j\">oops1<b id=\"5\">foo<c id=\"7\"/></b>oops2<b id=\"9\" p:x=\"y\">"
      + "<c id=\"11\"/>bar</b>oops3</p:a>";

  /** String representation of rest. */
  public static final String REST = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
      + "<rest:sequence xmlns:rest=\"https://sirix.io/rest\"><rest:item>"
      + "<p:a xmlns:p=\"ns\" rest:id=\"1\" i=\"j\">oops1<b rest:id=\"5\">foo<c rest:id=\"7\"/></b>oops2<b rest:id=\"9\" p:x=\"y\">"
      + "<c rest:id=\"11\"/>bar</b>oops3</p:a></rest:item></rest:sequence>";

  /** String representation of test document. */
  public static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
      + "<p:a xmlns:p=\"ns\" i=\"j\">oops1<b>foo<c/></b>oops2<b p:x=\"y\"><c/>bar</b>oops3</p:a>";

  /** String representation of test document. */
  public static final String COMMENTPIXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
      + "<p:a xmlns:p=\"ns\" i=\"j\"><!-- foo -->oops1<b>foo<c/></b><?bar baz=\"foo\"?>oops2<b p:x=\"y\">"
      + "<c/>bar</b>oops3</p:a>";

  /** String representation of test document without xml declaration. */
  public static final String XML_WITHOUT_XMLDECL =
      "<p:a xmlns:p=\"ns\" i=\"j\">oops1<b>foo<c/></b>oops2<b p:x=\"y\"><c/>bar</b>oops3</p:a>";

  /** String representation of versioned test document. */
  public static final String VERSIONEDXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
      + "<sdb:sirix xmlns:sdb=\"https://sirix.io/rest\"><sdb:sirix-item sdb:revision=\"1\"><p:a xmlns:p=\"ns\" i=\"j\">oops1<b>foo<c/></b>oops2<b p:x=\"y\"><c/>bar</b>oops3</p:a></sdb:sirix-item>"
      + "<sdb:sirix-item sdb:revision=\"2\"><p:a xmlns:p=\"ns\" i=\"j\"><p:a>OOPS4!</p:a>oops1<b>foo<c/></b>oops2<b p:x=\"y\"><c/>bar</b>oops3</p:a></sdb:sirix-item>"
      + "<sdb:sirix-item sdb:revision=\"3\"><p:a xmlns:p=\"ns\" i=\"j\"><p:a>OOPS4!</p:a><p:a>OOPS4!</p:a>oops1<b>foo<c/></b>oops2<b p:x=\"y\"><c/>bar</b>oops3</p:a></sdb:sirix-item></sdb:sirix>";

  /** String representation of test document without attributes. */
  public static final String XMLWITHOUTATTRIBUTES = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
      + "<p:a>oops1<b>foo<c></c></b>oops2<b>" + "<c></c>bar</b>oops3</p:a>";

  /** XML for the index structure. */
  public static final String XML_INDEX = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
      + "<t:o><t:oo><t:oop><t:oops><d:DOCUMENT_ROOT_KIND nodeID=\"0\"><d:p:a nodeID=\"1\">"
      + "<d:TEXT_KIND nodeID=\"4\"/></d:p:a></d:DOCUMENT_ROOT_KIND></t:oops></t:oop></t:oo>"
      + "</t:o><t:f><t:fo><t:foo><d:DOCUMENT_ROOT_KIND nodeID=\"0\"><d:p:a nodeID=\"1\">"
      + "<d:b nodeID=\"5\"><d:TEXT_KIND nodeID=\"6\"/></d:b></d:p:a></d:DOCUMENT_ROOT_KIND></t:foo>"
      + "</t:fo></t:f><t:b><t:ba><t:bar><d:DOCUMENT_ROOT_KIND nodeID=\"0\"><d:p:a nodeID=\"1\">"
      + "<d:b nodeID=\"9\"><d:TEXT_KIND nodeID=\"12\"/></d:b></d:p:a></d:DOCUMENT_ROOT_KIND></t:bar>" + "</t:ba></t:b>";

  /**
   * Private Constructor, not used.
   */
  private XmlDocumentCreator() {
    throw new AssertionError("Not permitted to call constructor!");
  }

  /**
   * Create simple test document containing all supported node kinds.
   *
   * @param wtx {@link XmlNodeTrx} to write to
   * @throws SirixException if anything weird happens
   */
  public static void createCommentPI(final XmlNodeTrx wtx) throws SirixException {
    wtx.moveToDocumentRoot();

    wtx.insertElementAsFirstChild(new QNm("ns", "p", "a"));
    wtx.insertNamespace(new QNm("ns", "p", "xmlns"));
    wtx.moveToParent();
    wtx.insertAttribute(new QNm("i"), "j");
    wtx.moveToParent();

    wtx.insertCommentAsFirstChild("foo");
    wtx.insertTextAsRightSibling("oops1");

    wtx.insertElementAsRightSibling(new QNm("b"));

    wtx.insertTextAsFirstChild("foo");
    wtx.insertElementAsRightSibling(new QNm("c"));
    wtx.moveToParent();

    wtx.insertPIAsRightSibling("bar", "baz=\"foo\"");
    wtx.insertTextAsRightSibling("oops2");

    wtx.insertElementAsRightSibling(new QNm("b"));
    wtx.insertAttribute(new QNm("ns", "p", "x"), "y");
    wtx.moveToParent();

    wtx.insertElementAsFirstChild(new QNm("c"));
    wtx.insertTextAsRightSibling("bar");
    wtx.moveToParent();

    wtx.insertTextAsRightSibling("oops3");

    wtx.moveToDocumentRoot();
  }

  /**
   * Create simple test document containing all supported node kinds except comment- and processing
   * instructions.
   *
   * @param wtx {@link XmlNodeTrx} to write to
   * @throws SirixException if anything weird happens
   */
  public static void create(final XmlNodeTrx wtx) throws SirixException {
    wtx.moveToDocumentRoot();

    wtx.insertElementAsFirstChild(new QNm("ns", "p", "a"));
    wtx.insertNamespace(new QNm("ns", "p", ""));
    wtx.moveToParent();
    wtx.insertAttribute(new QNm("i"), "j");
    wtx.moveToParent();

    wtx.insertTextAsFirstChild("oops1");

    wtx.insertElementAsRightSibling(new QNm("b"));

    wtx.insertTextAsFirstChild("foo");
    wtx.insertElementAsRightSibling(new QNm("c"));
    wtx.moveToParent();

    wtx.insertTextAsRightSibling("oops2");

    wtx.insertElementAsRightSibling(new QNm("b"));
    wtx.insertAttribute(new QNm("ns", "p", "x"), "y");
    wtx.moveToParent();

    wtx.insertElementAsFirstChild(new QNm("c"));
    wtx.insertTextAsRightSibling("bar");
    wtx.moveToParent().hasMoved();

    wtx.insertTextAsRightSibling("oops3");

    wtx.moveToDocumentRoot();
  }

  /**
   * Create simple revision test in current database.
   *
   * @param wtx {@link XmlNodeTrx} to write to
   * @throws SirixException if anything went wrong
   */
  public static void createVersioned(final XmlNodeTrx wtx) {
    create(wtx);
    wtx.commit();
    for (int i = 0; i <= 1; i++) {
      wtx.moveToDocumentRoot();
      wtx.moveToFirstChild();
      wtx.insertElementAsFirstChild(new QNm("ns", "p", "a"));
      wtx.insertTextAsFirstChild("OOPS4!");
      wtx.commit();
    }
  }

  /**
   * Create simple revision test in current database.
   *
   * @param wtx {@link XmlNodeTrx} to write to
   * @throws SirixException if anything went wrong
   */
  public static void createVersionedWithUpdatesAndDeletes(final XmlNodeTrx wtx) {
    create(wtx);
    wtx.commit();
    for (int i = 0; i <= 1; i++) {
      wtx.moveToDocumentRoot();
      wtx.moveToFirstChild();
      wtx.insertElementAsFirstChild(new QNm("a"));
      wtx.insertAttribute(new QNm("att"), "attval").moveToParent();
      wtx.insertTextAsFirstChild("OOPS4!");
      wtx.commit();
    }

    wtx.moveToDocumentRoot().trx().moveToFirstChild().trx().moveToLastChild().trx();
    wtx.remove();
    wtx.commit();

    wtx.moveTo(4);
    wtx.setValue("fooooooo");
    wtx.commit();
  }

  /**
   * Create simple test document containing all supported node kinds except the attributes.
   *
   * @param paramWtx {@link XmlNodeTrx} to write to
   * @throws SirixException if anything went wrong
   */
  public static void createWithoutAttributes(final XmlNodeTrx wtx) {
    wtx.moveToDocumentRoot();
    wtx.insertElementAsFirstChild(new QNm("ns", "p", "a"));
    wtx.insertTextAsFirstChild("oops1");
    wtx.insertElementAsRightSibling(new QNm("b"));
    wtx.insertTextAsFirstChild("foo");
    wtx.insertElementAsRightSibling(new QNm("c"));
    wtx.moveToParent();
    wtx.insertTextAsRightSibling("oops2");
    wtx.insertElementAsRightSibling(new QNm("b"));
    wtx.insertElementAsFirstChild(new QNm("c"));
    wtx.insertTextAsRightSibling("bar");
    wtx.moveToParent();
    wtx.insertTextAsRightSibling("oops3");
    wtx.moveToDocumentRoot();
  }

  /**
   * Create simple test document containing all supported node kinds, but ignoring their namespace
   * prefixes.
   *
   * @param wtx {@link XmlNodeTrx} to write to
   * @throws SirixException if anything went wrong
   */
  public static void createWithoutNamespace(final XmlNodeTrx wtx) throws SirixException {
    wtx.moveToDocumentRoot();
    wtx.insertElementAsFirstChild(new QNm("a"));
    wtx.insertAttribute(new QNm("i"), "j");
    wtx.moveToParent();
    wtx.insertTextAsFirstChild("oops1");
    wtx.insertElementAsRightSibling(new QNm("b"));
    wtx.insertTextAsFirstChild("foo");
    wtx.insertElementAsRightSibling(new QNm("c"));
    wtx.moveToParent();
    wtx.insertTextAsRightSibling("oops2");
    wtx.insertElementAsRightSibling(new QNm("b"));
    wtx.insertAttribute(new QNm("x"), "y");
    wtx.moveToParent();
    wtx.insertElementAsFirstChild(new QNm("c"));
    wtx.insertTextAsRightSibling("bar");
    wtx.moveToParent();
    wtx.insertTextAsRightSibling("oops3");
    wtx.moveToDocumentRoot();
  }

  /**
   * Create revisioned document.
   *
   * @throws SirixException if shredding fails
   * @throws XMLStreamException if StAX reader couldn't be created
   * @throws IOException if reading XML string fails
   */
  public static void createRevisioned(final Database<XmlResourceManager> database) {

    try (final XmlResourceManager resMgr = database.openResourceManager("resource")) {
      try (final XmlNodeTrx firstWtx = resMgr.beginNodeTrx()) {
        final XmlShredder shredder = new XmlShredder.Builder(firstWtx, XmlShredder.createStringReader(REVXML),
            InsertPosition.AS_FIRST_CHILD).commitAfterwards().build();
        shredder.call();
      }

      try (final XmlNodeTrx secondWtx = resMgr.beginNodeTrx()) {
        secondWtx.moveToFirstChild();
        secondWtx.moveToFirstChild();
        secondWtx.moveToFirstChild();
        secondWtx.setValue("A Contrived Test Document");
        secondWtx.moveToParent();
        secondWtx.moveToRightSibling();
        secondWtx.moveToRightSibling();
        secondWtx.moveToFirstChild();
        secondWtx.moveToRightSibling();
        final long key = secondWtx.getNodeKey();
        secondWtx.insertAttribute(new QNm("role"), "bold");
        secondWtx.moveTo(key);
        secondWtx.moveToRightSibling();
        secondWtx.setValue("changed in it.");
        secondWtx.moveToParent();
        secondWtx.insertElementAsRightSibling(new QNm("para"));
        secondWtx.insertTextAsFirstChild("This is a new para 2b.");
        secondWtx.moveToParent();
        secondWtx.moveToRightSibling();
        secondWtx.moveToRightSibling();
        secondWtx.moveToFirstChild();
        secondWtx.setValue("This is a different para 4.");
        secondWtx.moveToParent();
        secondWtx.insertElementAsRightSibling(new QNm("para"));
        secondWtx.insertTextAsFirstChild("This is a new para 4b.");
        secondWtx.moveToParent();
        secondWtx.moveToRightSibling();
        secondWtx.moveToRightSibling();
        secondWtx.remove();
        secondWtx.remove();
        secondWtx.commit();
        secondWtx.moveToDocumentRoot();
        secondWtx.moveToFirstChild();
        secondWtx.moveToFirstChild();
        secondWtx.remove();
        secondWtx.commit();
      }
    }
  }
}
