package com.baeldung.uncheckedconversion;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UncheckedConversionUnitTest {

    @Test
    public void givenRawList_whenAssignToTypedList_shouldHaveCompilerWarning() {
        List<String> fromRawList = UncheckedConversion.getRawList();
        Assert.assertEquals(3, fromRawList.size());
        Assert.assertEquals("I am the 1st String.", fromRawList.get(0));
    }

    @Test(expected = ClassCastException.class)
    public void givenRawList_whenListHasMixedType_shouldThrowClassCastException() {
        List<String> fromRawList = UncheckedConversion.getRawListWithMixedTypes();
        Assert.assertEquals(4, fromRawList.size());
        Assert.assertFalse(fromRawList.get(3).endsWith("String."));
    }

    @Test
    public void givenRawList_whenAssignToTypedListAfterCallingCastList_shouldOnlyHaveElementsWithExpectedType() {
        List rawList = UncheckedConversion.getRawListWithMixedTypes();
        List<String> strList = UncheckedConversion.castList(String.class, rawList);
        Assert.assertEquals(4, rawList.size());
        Assert.assertEquals("One element with the wrong type has been filtered out.", 3, strList.size());
        Assert.assertTrue(strList.stream().allMatch(el -> el.endsWith("String.")));
    }

    @Test(expected = ClassCastException.class)
    public void givenRawListWithWrongType_whenAssignToTypedListAfterCallingCastList2_shouldThrowException() {
        List rawList = UncheckedConversion.getRawListWithMixedTypes();
        UncheckedConversion.castList2(String.class, rawList);
    }

}
