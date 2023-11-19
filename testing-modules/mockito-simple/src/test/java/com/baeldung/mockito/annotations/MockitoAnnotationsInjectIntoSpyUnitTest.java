package com.baeldung.mockito.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.mockito.MyDictionary;

@ExtendWith(MockitoExtension.class)
class MockitoAnnotationsInjectIntoSpyUnitTest {

    @BeforeEach
    public void init() {
        openMocks(this);
        spyDic = spy(new MyDictionary(wordMap));
    }

    @Mock
    private Map<String, String> wordMap;

    @InjectMocks
    private MyDictionary dic = new MyDictionary();

    private MyDictionary spyDic;

    @Test
    void whenUseInjectMocksAnnotation_thenCorrect() {
        when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", spyDic.getMeaning("aWord"));
    }
}
