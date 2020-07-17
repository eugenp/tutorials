package com.baeldung.hexarchi.domain.entities;

import java.util.List;

public class PhoneBook {

    private Long id;
    private List<String> numbers;

    public PhoneBook(Long id, List<String> numbers) {
        this.id = id;
        this.numbers = numbers;
    }

    public void addNumber(String number) {
        numbers.add(number);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }
}
