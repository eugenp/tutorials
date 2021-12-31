package com.baeldung.hexagonal;

import com.baeldung.hexagonal.bo.BusinessLogic;

public class Hexagonal {

    public static void main(String[] args) {
        BusinessLogic logic = new BusinessLogic();
        logic.processUsers();
    }
}
