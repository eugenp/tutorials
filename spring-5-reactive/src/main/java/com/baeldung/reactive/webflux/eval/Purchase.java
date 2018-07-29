package com.baeldung.reactive.webflux.eval;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Purchase {
    private String id;
    private Date date;
}
