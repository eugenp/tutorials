package com.baeldung.customfunc;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@NamedStoredProcedureQuery(
        name = "Product.sha256Hex",
        procedureName = "SHA256_HEX",
        parameters = @StoredProcedureParameter(mode = ParameterMode.IN, name = "value", type = String.class)
)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    private String name;

}