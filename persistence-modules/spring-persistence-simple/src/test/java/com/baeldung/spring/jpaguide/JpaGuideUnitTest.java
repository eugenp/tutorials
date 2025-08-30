package com.baeldung.spring.jpaguide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.baeldung.spring.jpa.guide.model.Publishers;
import com.baeldung.spring.jpa.guide.repository.PublisherRepository;
import com.baeldung.spring.jpa.guide.service.PublisherService;

public class JpaGuideUnitTest {

    @Test
    public void givenPublisher_whenSave_thenPublisherIsSaved() {
        PublisherRepository publisherRepository = mock(PublisherRepository.class);
        PublisherService publisherService = spy(new PublisherService(publisherRepository));
        Publishers publishers = new Publishers("Springer", "Dallas", 10);
        when(publisherRepository.save(any(Publishers.class))).thenReturn(publishers);
        publisherService.save(publishers);
        verify(publisherRepository, times(1)).save(any(Publishers.class));
    }

    @Test
    public void givenPublishers_whenFindAll_thenReturnAllPublishers() {
        PublisherRepository publisherRepository = mock(PublisherRepository.class);
        PublisherService publisherService = new PublisherService(publisherRepository);
        List<Publishers> publishersList = Arrays.asList(new Publishers("Springer", "Dallas", 10), new Publishers("O'Reilly", "San Francisco", 20));
        when(publisherRepository.findAll()).thenReturn(publishersList);

        List<Publishers> result = publisherService.findAll();

        assertEquals(2, result.size());
        verify(publisherRepository, times(1)).findAll();
    }

    @Test
    public void givenPublisherId_whenFindById_thenReturnPublisher() {
        PublisherRepository publisherRepository = mock(PublisherRepository.class);
        PublisherService publisherService = new PublisherService(publisherRepository);
        Publishers publisher = new Publishers("Springer", "Dallas", 10);
        when(publisherRepository.findById(anyInt())).thenReturn(Optional.of(publisher));

        Publishers result = publisherService.findById(1);

        assertNotNull(result);
        verify(publisherRepository, times(1)).findById(anyInt());
    }

    @Test
    public void givenPublisher_whenUpdate_thenPublisherIsUpdated() {
        PublisherRepository publisherRepository = mock(PublisherRepository.class);
        PublisherService publisherService = new PublisherService(publisherRepository);
        Publishers publisher = new Publishers("Springer", "Dallas", 10);
        when(publisherRepository.save(any(Publishers.class))).thenReturn(publisher);

        Publishers result = publisherService.update(publisher);

        assertNotNull(result);
        verify(publisherRepository, times(1)).save(any(Publishers.class));
    }

    @Test
    public void givenLocation_whenFindAllByLocation_thenReturnPublishersInLocation() {
        PublisherRepository publisherRepository = mock(PublisherRepository.class);
        PublisherService publisherService = new PublisherService(publisherRepository);
        List<Publishers> publishersList = Arrays.asList(new Publishers("Springer", "Dallas", 10), new Publishers("O'Reilly", "Dallas", 20));
        when(publisherRepository.findAllByLocation(anyString())).thenReturn(publishersList);

        List<Publishers> result = publisherService.findAllByLocation("Dallas");

        assertEquals(2, result.size());
        verify(publisherRepository, times(1)).findAllByLocation(anyString());
    }

    @Test
    public void givenMinJournalsAndLocation_whenFindPublishersWithMinJournalsInLocation_thenReturnPublishers() {
        PublisherRepository publisherRepository = mock(PublisherRepository.class);
        PublisherService publisherService = new PublisherService(publisherRepository);
        List<Publishers> publishersList = Arrays.asList(new Publishers("Springer", "Dallas", 10), new Publishers("O'Reilly", "Dallas", 20));
        when(publisherRepository.findPublishersWithMinJournalsInLocation(anyInt(), anyString())).thenReturn(publishersList);

        List<Publishers> result = publisherService.findPublishersWithMinJournalsInLocation(5, "Dallas");

        assertEquals(2, result.size());
        verify(publisherRepository, times(1)).findPublishersWithMinJournalsInLocation(anyInt(), anyString());
    }
}
