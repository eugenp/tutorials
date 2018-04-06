package com.baeldung.easymock;

import org.easymock.*;
import org.junit.*;

import static org.easymock.EasyMock.*;

public class BaeldungReaderMockDelegationTest {

    EasyMockSupport easyMockSupport = new EasyMockSupport();

    @Test
    public void givenBaeldungReader_whenReadAndWrite_thenOperationSupported() {
        ArticleReader mockArticleReader = easyMockSupport.createMock(ArticleReader.class);
        IArticleWriter mockArticleWriter = easyMockSupport.createMock(IArticleWriter.class);
        BaeldungReader baeldungReader = new BaeldungReader(mockArticleReader, mockArticleWriter);

        expect(mockArticleReader.next()).andReturn(null);
        expect(mockArticleWriter.write("title", "content")).andReturn("");
        easyMockSupport.replayAll();

        baeldungReader.readNext();
        baeldungReader.write("title", "content");
        easyMockSupport.verifyAll();
    }

}