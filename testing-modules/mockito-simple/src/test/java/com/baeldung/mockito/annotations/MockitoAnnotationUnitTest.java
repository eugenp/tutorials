package com.baeldung.mockito.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.mockito.MyDictionary;

@ExtendWith(MockitoExtension.class)
class MockitoAnnotationUnitTest {

    @Mock
    private List<String> mockedList;

    @Spy
    private List<String> spiedList = new ArrayList<>();

    // Use either @ExtendWith(MockitoExtension.class) or manually openMocks in the @BeforeEach method
    /*
    @BeforeEach
    public void init() {
    	MockitoAnnotations.openMocks(this);
    }
    */

    // tests

    @Test
    void whenNotUseMockAnnotation_thenCorrect() {
        final List<String> mockList = mock(ArrayList.class);

        mockList.add("one");
        verify(mockList).add("one");
        assertEquals(0, mockList.size());

        when(mockList.size()).thenReturn(100);
        assertEquals(100, mockList.size());
    }

    @Test
    void whenUseMockAnnotation_thenMockIsInjected() {
        mockedList.add("one");
        verify(mockedList).add("one");
        assertEquals(0, mockedList.size());

        when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
    }

    @Test
    void whenNotUseSpyAnnotation_thenCorrect() {
        final List<String> spyList = spy(new ArrayList<String>());

        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertEquals(2, spyList.size());

        doReturn(100).when(spyList).size();
        assertEquals(100, spyList.size());
    }

    @Test
    void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
        spiedList.add("one");
        spiedList.add("two");

        verify(spiedList).add("one");
        verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        doReturn(100).when(spiedList).size();
        assertEquals(100, spiedList.size());
    }

    @Test
    void whenNotUseCaptorAnnotation_thenCorrect() {
        final List<String> mockList = mock(List.class);
        final ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

        mockList.add("one");
        verify(mockList).add(arg.capture());

        assertEquals("one", arg.getValue());
    }

    @Captor
    private ArgumentCaptor<String> argCaptor;

    @Test
    void whenUseCaptorAnnotation_thenTheSame() {
        mockedList.add("one");
        verify(mockedList).add(argCaptor.capture());

        assertEquals("one", argCaptor.getValue());
    }

    @Mock
    private Map<String, String> wordMap;

    @InjectMocks
    private MyDictionary dic = new MyDictionary();

    @Test
    void whenUseInjectMocksAnnotation_thenCorrect() {
        when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", dic.getMeaning("aWord"));
    }
}
