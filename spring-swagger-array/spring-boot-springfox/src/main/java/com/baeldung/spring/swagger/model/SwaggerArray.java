package com.baeldung.spring.swagger.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class SwaggerArray {
    @Id
    @GeneratedValue
    private int id;

    @ElementCollection(targetClass = String.class)
    @ApiModelProperty(name = "array", dataType="List", example = "[\"str1\", \"str2\", \"str3\"]")
    private List<String> array;
}
