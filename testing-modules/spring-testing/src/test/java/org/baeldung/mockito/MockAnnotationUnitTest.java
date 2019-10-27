package org.baeldung.mockito;

import org.baeldung.mockito.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockAnnotationUnitTest {
    
    @Mock
    UserRepository mockRepository;

    @Test
    public void givenCountMethodMocked_WhenCountInvoked_ThenMockValueReturned() {
        Mockito.when(mockRepository.count()).thenReturn(123L);

        long userCount = mockRepository.count();

        Assert.assertEquals(123L, userCount);
        Mockito.verify(mockRepository).count();
    }
    
    @Test
    public void givenCountMethodMocked_WhenCountInvoked_ThenMockedValueReturned() {
        UserRepository localMockRepository = Mockito.mock(UserRepository.class);
        Mockito.when(localMockRepository.count()).thenReturn(111L);

        long userCount = localMockRepository.count();

        Assert.assertEquals(111L, userCount);
        Mockito.verify(localMockRepository).count();
    }
}
