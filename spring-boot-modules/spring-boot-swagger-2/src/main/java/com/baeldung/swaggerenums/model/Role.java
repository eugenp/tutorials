package com.baeldung.swaggerenums.model;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum Role {
    Engineer, Clerk, Driver, Janitor;
}