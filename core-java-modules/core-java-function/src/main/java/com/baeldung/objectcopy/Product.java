package com.baeldung.objectcopy;

import java.util.ArrayList;
import java.util.List;

public class Product{

    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void shallowCopy(List<String> images) {
        this.images = images;
    }

    public void deepCopy(List<String> images) {
        this.images = new ArrayList<>();
        for (String image: images) {
            this.images.add(image);
        }
    }

}