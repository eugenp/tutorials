package org.baeldung.mockito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MockitoInjectIntoSpyUnitTest {

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        spyDic = Mockito.spy(new MyDictionary(wordMap));
    }

    @Mock
    private Map<String, String> wordMap;

    @InjectMocks
    private MyDictionary dic = new MyDictionary();

    private MyDictionary spyDic;

    @Test
    public void whenUseInjectMocksAnnotation_thenCorrect2() {
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", spyDic.getMeaning("aWord"));
    }
}
