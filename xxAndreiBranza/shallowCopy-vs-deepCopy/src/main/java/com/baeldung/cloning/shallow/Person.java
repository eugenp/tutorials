package com.baeldung.cloning.shallow;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Person implements Cloneable
{
	private String firstName;
	private String lastName;
	private Address address;

	@Override
	public Person clone()  {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException cloneException) {
            throw new RuntimeException(cloneException);
        }
    }
}
