package com.baeldung.concatenate

class Wonder {

    String numOfWonder = 'seven'

    String operator_plus() {
        return 'The ' + numOfWonder + ' wonders of the world'
    }

    String operator_left() {
        return 'The ' << numOfWonder << ' wonders of ' << 'the world'
    }

    String interpolation_one() {
        return "The $numOfWonder wonders of the world"

    }

    String interpolation_two() {
        return "The ${numOfWonder} wonders of the world"
    }

    String multilineString() {
        return """
            There are $numOfWonder wonders of the world.
            Can you name them all? 
            1. The Great Pyramid of Giza
            2. Hanging Gardens of Babylon
            3. Colossus of Rhode
            4. Lighthouse of Alexendra
            5. Temple of Artemis
            6. Status of Zeus at Olympia
            7. Mausoleum at Halicarnassus
        """
    }

    String method_concat() {
        return 'The '.concat(numOfWonder).concat(' wonders of the world')

    }

    String method_builder() {
        return new StringBuilder()
                .append('The ').append(numOfWonder).append(' wonders of the world')
    }

    String method_buffer() {
        return new StringBuffer()
                .append('The ').append(numOfWonder).append(' wonders of the world')
    }
}