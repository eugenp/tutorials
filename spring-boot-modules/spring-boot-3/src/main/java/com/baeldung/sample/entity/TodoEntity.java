package com.baeldung.sample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/*
 * Auch hier wird eine separate Klasse erstellt.
 * Und auch hier geht es wieder um Unabhängigkeit der beiden Layer (Control und Persistence).
 * So kann z.B. die Auflösung von Fremdschlüsseln (assignee, priority, topic) in der Persistence Layer erfolgen (JPA unterstützt das), oder aber auch erst in der Control Layer.
 */
@Entity(name = "todo")
@Table(name = "todos")
// we do not use @Data because hashCode() and equals() might influence JPA's behaviour
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TodoEntity {

    public enum StatusEntity {
        NEW, PROGRESS, COMPLETED, ARCHIVED
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @NotNull
    @Size(min = 1)
    private String title;
    private String description;
    private LocalDate dueDate;
    @Enumerated
    @NotNull
    private StatusEntity status = StatusEntity.NEW;

    public TodoEntity(Long id, String title) {
        this.id = id;
        this.title = title;
    }

}
