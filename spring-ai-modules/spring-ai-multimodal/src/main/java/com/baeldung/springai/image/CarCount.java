package com.baeldung.springai.image;

import java.util.List;

public class CarCount {

    private List<CarColorCount> carColorCounts;
    private int totalCount;

    public CarCount() {
    }

    public List<CarColorCount> getCarColorCounts() {
        return carColorCounts;
    }

    public void setCarColorCounts(List<CarColorCount> carColorCounts) {
        this.carColorCounts = carColorCounts;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
