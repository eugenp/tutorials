package com.baeldung.spring.cloudfunction;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

public class StringReverseHandler extends SpringBootRequestHandler<String, String> {

}
