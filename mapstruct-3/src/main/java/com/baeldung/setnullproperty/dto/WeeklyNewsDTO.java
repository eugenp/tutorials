package com.baeldung.setnullproperty.dto;

import java.util.Objects;

public class WeeklyNewsDTO extends ReviewableDTO {

    private String title;

    public WeeklyNewsDTO() {
    }

    public WeeklyNewsDTO(String title1) {
        this.title = title1;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        WeeklyNewsDTO that = (WeeklyNewsDTO) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title);
    }
}
