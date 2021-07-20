package com.baeldung.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
   
    private Long id;
    private String title;
    private String description;
    private Double price;

}
