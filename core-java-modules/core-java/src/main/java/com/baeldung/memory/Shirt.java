package com.baeldung.memory;

public class Shirt {
    private final float lengthInCms;
    private final float widthInCms;
    private final float size;
    private final float ageInYears;
    private final Cloth cloth;

    public Shirt(float lengthInCms, float widthInCms, float size, float ageInYears, Cloth cloth) {
        this.lengthInCms = lengthInCms;
        this.widthInCms = widthInCms;
        this.size = size;
        this.ageInYears = ageInYears;
        this.cloth = cloth;
    }

    public Cloth getCloth() {
        return cloth;
    }

    public Shirt shallowCopy() {
        return new Shirt(
                lengthInCms, widthInCms, size, ageInYears, cloth);
    }

    public Shirt deepCopy() {
        return new Shirt(
                lengthInCms, widthInCms, size, ageInYears, new Cloth(cloth.getType(), cloth.getColor()));
    }

    @Override
    public String toString() {
        return "Shirt{" +
                "lengthInCms=" + lengthInCms +
                ", widthInCms=" + widthInCms +
                ", size=" + size +
                ", ageInYears=" + ageInYears +
                ", cloth=" + cloth +
                '}';
    }
}
