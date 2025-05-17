package com.baeldung.internalaop;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class AddOneAndDoubleComponent {

    @Resource
    private AddComponent addComponent;

    public int addOneAndDouble(int n) {
        return addComponent.addOne(n) + addComponent.addOne(n);
    }
}
