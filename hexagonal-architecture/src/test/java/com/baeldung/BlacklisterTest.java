package com.baeldung;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BlacklisterTest {

    @Mock
    private FilePersistence filePersistence;
    @Mock
    private TravelerStore travelerStore;
    @Mock
    Traveler traveler;
    private Blacklister blacklister;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        blacklister = new Blacklister(filePersistence, travelerStore);
        when(travelerStore.storeHasTraveler(traveler)).thenReturn(true);
    }

    @Test
    public void whenTravelerDoesNotHaveCriminalRecord_DoNotBlacklistIt() throws Exception {
        when(traveler.hasCriminalRecord()).thenReturn(false);
        blacklister.blacklist(traveler);
        verify(filePersistence, never()).addToBlacklist(traveler);
    }

    @Test
    public void whenTravelerHasCriminalRecord_BlacklistIt() throws Exception {
        when(traveler.hasCriminalRecord()).thenReturn(true);
        blacklister.blacklist(traveler);
        verify(filePersistence).addToBlacklist(traveler);
    }
}