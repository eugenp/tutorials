package com.baeldung.cloning.shallow;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Address implements Cloneable
{
	private String streetName;
	private String cityName;

	@Override
	public Address clone() {
		try
		{
			return (Address) super.clone();
		} catch (CloneNotSupportedException cloneException)
		{
			throw new RuntimeException(cloneException);
		}
	}
}