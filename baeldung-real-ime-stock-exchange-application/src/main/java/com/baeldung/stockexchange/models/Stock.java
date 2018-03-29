package com.baeldung.stockexchange.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Stock")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Stock
{
	@Id
	private String stockId;

	@NotNull
	@Size(min = 3, max = 50, message = "Stock name must be between 3 and 50 characters.")
	private String stockName;

	@NotNull
	private float stockPrice;
}
