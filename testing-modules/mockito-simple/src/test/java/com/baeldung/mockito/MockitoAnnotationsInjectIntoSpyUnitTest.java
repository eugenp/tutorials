package com.baeldung.mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;


@ExtendWith(MockitoExtension.class)
public class MockitoAnnotationsInjectIntoSpyUnitTest {

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        spyDic = Mockito.spy(new MyDictionary(wordMap));
    }

    @Mock
    private Map<String, String> wordMap;

    @InjectMocks
    private MyDictionary dic = new MyDictionary();

    private MyDictionary spyDic;

    @Test
    public void whenUseInjectMocksAnnotation_thenCorrect() {
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", spyDic.getMeaning("aWord"));
    }
}
