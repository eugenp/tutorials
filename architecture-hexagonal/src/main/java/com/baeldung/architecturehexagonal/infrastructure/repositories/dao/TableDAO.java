package com.baeldung.architecturehexagonal.infrastructure.repositories.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.baeldung.architecturehexagonal.domain.model.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TableDAO {
    @Id
    private Long id;
    private Integer number;
    private Integer capacity;

    public Table toTable() {
        return Table.of(this.id, this.number, this.capacity);
    }

    public static TableDAO fromTable(Table table) {
        return new TableDAO(table.getId(), table.getNumber(), table.getCapacity());
    }
}
