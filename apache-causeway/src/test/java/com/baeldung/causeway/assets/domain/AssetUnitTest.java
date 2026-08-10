package com.baeldung.causeway.assets.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class AssetUnitTest {

    @Test
    void whenAssigningAvailableAssetThenChangesItsState() {
        final Asset asset = new Asset(AssetType.LAPTOP, "LT-100");

        assertNull(asset.disableAssignTo());
        assertSame(asset, asset.assignTo("  Ada Lovelace  "));
        assertEquals(AssetStatus.ASSIGNED, asset.getStatus());
        assertEquals("Ada Lovelace", asset.getAssignedTo());
        assertEquals("Only available assets can be assigned", asset.disableAssignTo());
    }

    @Test
    void whenReturningAssignedAssetThenMakesItAvailable() {
        final Asset asset = new Asset(AssetType.PHONE, "PH-200");
        asset.assignTo("Grace Hopper");

        assertNull(asset.disableReturnToInventory());
        assertSame(asset, asset.returnToInventory());
        assertEquals(AssetStatus.AVAILABLE, asset.getStatus());
        assertNull(asset.getAssignedTo());
        assertEquals("Only assigned assets can be returned", asset.disableReturnToInventory());
    }

    @Test
    void whenRetiringAvailableAssetThenPreventsFurtherTransitions() {
        final Asset asset = new Asset(AssetType.MONITOR, "MN-300");

        assertNull(asset.disableRetire());
        assertSame(asset, asset.retire());
        assertEquals(AssetStatus.RETIRED, asset.getStatus());
        assertEquals("The asset is already retired", asset.disableRetire());
        assertEquals("Only available assets can be assigned", asset.disableAssignTo());
    }

    @Test
    void whenEvaluatingRulesThenReturnsExplanatoryMessages() {
        final Asset asset = new Asset(AssetType.LAPTOP, "LT-400");

        assertEquals("Employee name is required", asset.validate0AssignTo("  "));
        assertNull(asset.validate0AssignTo("Katherine Johnson"));
        assertEquals("Only assigned assets can be returned", asset.disableReturnToInventory());

        asset.assignTo("Katherine Johnson");

        assertEquals("Return the asset before retiring it", asset.disableRetire());
    }
}
