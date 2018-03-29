package com.baeldung.stockexchange.models;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceChange
{
	private Stock stock;
	private Date date;
}
