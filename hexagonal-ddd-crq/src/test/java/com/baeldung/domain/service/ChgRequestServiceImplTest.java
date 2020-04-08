package com.baeldung.domain.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.baeldung.domain.AppMetadata;
import com.baeldung.domain.ChgRequest;
import com.baeldung.domain.repository.ChgRequestRepository;

class ChgRequestServiceImplTest {

    private ChgRequestServiceImpl chgRequestService;

    @Mock
    private ChgRequestRepository chgRequestRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        chgRequestService = new ChgRequestServiceImpl(chgRequestRepository);
    }

    @Test
    void shouldCreateChgRequest_thenSaveIt() {
        var alpha = new AppMetadata("alpha", "1.3.1", "random jira link", "prod-us-west-2", "rolling out new feature");

        var id = chgRequestService.createChgRequest(alpha);

        verify(chgRequestRepository).save(any(ChgRequest.class));
        assertNotNull(id);
    }
}
