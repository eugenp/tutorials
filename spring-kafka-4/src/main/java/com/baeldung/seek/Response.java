package com.baeldung.seek;

public record Response(int partition, long offset, String value) {

}
