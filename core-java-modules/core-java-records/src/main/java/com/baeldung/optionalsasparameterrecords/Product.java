package com.baeldung.optionalsasparameterrecords;

import java.util.Optional;

public record Product(String name, double price, Optional<String> description) {
}
