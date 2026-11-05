package com.baeldung.causeway.assets.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.apache.causeway.applib.services.repository.RepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AssetsUnitTest {

    @Mock
    private RepositoryService repositoryService;

    @Mock
    private AssetRepository assetRepository;

    private Assets assets;

    @BeforeEach
    void setUp() {
        assets = new Assets(repositoryService, assetRepository);
    }

    @Test
    void whenCreatingAssetThenPersistsAnAvailableAsset() {
        when(repositoryService.persist(any(Asset.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        final Asset asset = assets.create(AssetType.LAPTOP, "  LT-500  ");

        assertEquals("LT-500", asset.getSerialNumber());
        assertEquals(AssetStatus.AVAILABLE, asset.getStatus());
        verify(repositoryService).persist(asset);
    }

    @Test
    void whenListingAssetsThenUsesRepositoryOrdering() {
        final List<Asset> expected = List.of(new Asset(AssetType.PHONE, "PH-600"));
        when(assetRepository.findAllByOrderBySerialNumberAsc()).thenReturn(expected);

        final List<Asset> actual = assets.listAll();

        assertSame(expected, actual);
    }

    @Test
    void whenValidatingSerialNumberThenRejectsBlanksAndDuplicates() {
        assertEquals("Serial number is required", assets.validate1Create(" "));

        when(assetRepository.findBySerialNumberIgnoreCase("LT-700"))
            .thenReturn(Optional.of(new Asset(AssetType.LAPTOP, "LT-700")));

        assertEquals(
            "An asset with this serial number already exists",
            assets.validate1Create(" LT-700 ")
        );

        when(assetRepository.findBySerialNumberIgnoreCase("LT-701"))
            .thenReturn(Optional.empty());
        assertNull(assets.validate1Create("LT-701"));
    }
}
