package com.baeldung.easymock;

import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.*;

import java.util.NoSuchElementException;

import static org.easymock.EasyMock.*;

public class BaeldungReaderAnnotatedWithRuleUnitTest {

    @Rule public EasyMockRule mockRule = new EasyMockRule(this);

    @Mock ArticleReader mockArticleReader;

    @Mock IArticleWriter mockArticleWriter;

    @TestSubject BaeldungReader baeldungReader = new BaeldungReader();

    @Test
    public void givenBaeldungReader_whenReadNext_thenNextArticleRead() {
        expect(mockArticleReader.next()).andReturn(null);
        replay(mockArticleReader);
        baeldungReader.readNext();
        verify(mockArticleReader);
    }

    @Mock BaeldungReader mockBaeldungReader;

    @Test
    public void givenBaeldungReader_whenWrite_thenWriterCalled() {
        expect(mockArticleWriter.write("title", "content")).andReturn(null);
        replay(mockArticleWriter);
        baeldungReader.write("title", "content");
        verify(mockArticleWriter);
    }

    @Test
    public void givenArticlesInReader_whenReadTillEnd_thenThrowException() {
        expect(mockArticleReader.next())
          .andReturn(null)
          .times(2)
          .andThrow(new NoSuchElementException());
        replay(mockArticleReader);
        try {
            for (int i = 0; i < 3; i++) {
                baeldungReader.readNext();
            }
        } catch (Exception ignored) {
        }
        verify(mockArticleReader);
    }

}

