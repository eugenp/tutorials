package com.baeldung.hibernate.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.baeldung.hibernate.pojo.PersonName;

@Converter
public class PersonNameConverter implements AttributeConverter<PersonName, String> {

    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(PersonName personName) {
        if (personName == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (personName.getSurname() != null) {
            sb.append(personName.getSurname());
            sb.append(SEPARATOR);
        }

        if (personName.getName() != null) {
            sb.append(personName.getName());
        }

        return sb.toString();
    }

    @Override
    public PersonName convertToEntityAttribute(String dbPersonName) {
        if (dbPersonName == null) {
            return null;
        }

        String[] pieces = dbPersonName.split(SEPARATOR);

        if (pieces == null || pieces.length == 0) {
            return null;
        }

        PersonName personName = new PersonName();
        if (dbPersonName.contains(SEPARATOR)) {
            personName.setSurname(pieces[0]);

            if (pieces[1] != null) {
                personName.setName(pieces[1]);
            }
        } else {
            personName.setName(pieces[0]);
        }

        return personName;
    }

}
