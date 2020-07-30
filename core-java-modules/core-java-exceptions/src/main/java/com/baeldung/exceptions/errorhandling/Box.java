package com.baeldung.exceptions.errorhandling;

class Box {
    BoxResponseCode open() {
        // open box

        return BoxResponseCode.SUCCESS;
    }

    BoxResponseCode addToy(Toy toy) {
        // add toy

        return BoxResponseCode.SUCCESS;
    }

    BoxResponseCode close() {
        // close box

        return BoxResponseCode.SUCCESS;
    }
}
