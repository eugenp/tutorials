package com.baeldung.spring.redis.configuration.repository;

import com.baeldung.spring.redis.configuration.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BooksRepositoryUnitTest {

    @InjectMocks
    private BooksRepository booksRepository;

    @Mock
    private RedisTemplate<Long, Book> redisTemplate;

    @Mock
    private ValueOperations<Long, Book> valueOperations;

    private static final Long BOOK_ID = 1l;
    private static final Long OTHER_BOOK_ID = 2l;
    private static final String BOOK_NAME = "Ulysses";

    @Test
    public void whenSave_thenCorrect() {
        Book book = new Book(BOOK_ID, BOOK_NAME);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        booksRepository.save(book);
        verify(redisTemplate, times(1)).opsForValue();
        verify(valueOperations, times(1)).set(BOOK_ID, book);
    }

    @Test
    public void whenFindByIdFound_thenReturnsBook() {
        Book book = new Book(BOOK_ID, BOOK_NAME);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(BOOK_ID)).thenReturn(book);
        assertSame(book, booksRepository.findById(BOOK_ID));
        verify(redisTemplate, times(1)).opsForValue();
        verify(valueOperations, times(1)).get(BOOK_ID);
    }

    @Test
    public void whenFindByIdNotFound_thenReturnsNull() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        assertNull(booksRepository.findById(OTHER_BOOK_ID));
        verify(redisTemplate, times(1)).opsForValue();
        verify(valueOperations, times(1)).get(OTHER_BOOK_ID);
    }

}
