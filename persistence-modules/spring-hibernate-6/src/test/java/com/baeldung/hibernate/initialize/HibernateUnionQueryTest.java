package com.baeldung.hibernate.initialize;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.baeldung.hibernate.initialize.MyClassWithUnionMethod;

public class HibernateUnionQueryTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private NativeQuery<Object[]> nativeQuery;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock behavior for sessionFactory
        when(sessionFactory.getCurrentSession()).thenReturn(session);

        // Mock behavior for session.createNativeQuery()
        when(session.createNativeQuery(anyString())).thenReturn(nativeQuery);
    }

    @Test
    public void testExecuteUnionQuery() {
        // Mock data
        Object[] row1 = { 1L, "John" };
        Object[] row2 = { 2L, "Jane" };
        List<Object[]> expectedResult = new ArrayList<>();
        expectedResult.add(row1);
        expectedResult.add(row2);

        // Mock behavior for nativeQuery.list()
        when(nativeQuery.list()).thenReturn(expectedResult);

        // Create instance of class with executeUnionQuery() method
        MyClassWithUnionMethod myClass = new MyClassWithUnionMethod(sessionFactory);

        // Call the method to be tested
        List<Object[]> actualResult = myClass.executeUnionQuery();

        // Verify the method behavior
        verify(sessionFactory).getCurrentSession(); // Verify sessionFactory.getCurrentSession() was called
        verify(session).createNativeQuery(anyString()); // Verify session.createNativeQuery() was called with any string
        verify(nativeQuery).list(); // Verify nativeQuery.list() was called

        // Assert the result
        assertNotNull(actualResult);
        assertEquals(expectedResult.size(), actualResult.size());
        assertArrayEquals(expectedResult.get(0), actualResult.get(0));
        assertArrayEquals(expectedResult.get(1), actualResult.get(1));
    }
}