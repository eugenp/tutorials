package com.baeldung.java.concept;

import java.util.ArrayList;
import java.util.List;

public class UsageExamples {

    // Deep copy may be used when new separate objects with the same content are needed
    // In that sense, it's very similar to Prototype design pattern
    public void deepCopyUsage() {
        String snapdragonOldProcessor = "Snapdragon 695";
        CpuSpecification snapdragonSpec = new CpuSpecification(snapdragonOldProcessor, "2GHz");
        Smartphone samsungS10 = new Smartphone("Samsung", "S10", 499, snapdragonSpec);

        // When we need a new similar Samsung phone, just copy from the existing phone object
        Smartphone newSamsungS10 = samsungS10.deepCopy();

        // Or fill a collection with objects
        List<Smartphone> samsungS10Phones = new ArrayList<>();
        for (int i = 0; i < 100; ++i) {
            samsungS10Phones.add(samsungS10.deepCopy());
        }
    }

    // Shallow copy is more efficient than deep copy as it reuses reference type instances
    // instead of creating them from scratch, that saves memory space as well as CPU time.
    // Use it for read-only operations or for operations where non-reference fields of original object shouldn't change
    public void shallowCopyUsage() {
        String snapdragonOldProcessor = "Snapdragon 695";
        CpuSpecification snapdragonSpec = new CpuSpecification(snapdragonOldProcessor, "2GHz");
        Smartphone samsungS10 = new Smartphone("Samsung", "S10", 499, snapdragonSpec);

        // Let's calculate what the price of the phone would be if we used Intel processor instead of Snapdragon
        // We don't change the original object and meanwhile update the expected price in non-reference field of the shallow copy
        // We might do the same with deep copy, but it would create the copy from scratch with redundant copy operations of reference fields
        Smartphone shallowSamsungS10 = samsungS10.shallowCopy();
        if (shallowSamsungS10.getCpuSpecification().equals("Snapdragon 695")) {
            shallowSamsungS10.setPrice(shallowSamsungS10.getPrice() + 100);
        } else if (shallowSamsungS10.getCpuSpecification().equals("Snapdragon 700")) {
            shallowSamsungS10.setPrice(shallowSamsungS10.getPrice() + 50);
        }
    }

}

