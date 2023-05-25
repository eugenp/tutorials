package com.baeldung.sample.control;

import com.baeldung.sample.entity.TodosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Ein Service ist ein Singleton auf Control Layer, der in der Boundary von mehreren (REST) Controllern gemeinsam genutzt werden kann.
 * Dieser hat keinen Bezug mehr zu HTTP.
 */
@Service
@RequiredArgsConstructor
public class TodosService {

    private final TodoEntityMapper mapper;
    private final TodosRepository repo;

    /**
     * Gibt die Anzahl an Datensätzen zurück.
     * @return die Anzahl an Datensätzen
     */
    long count() {
        return repo.count();
    }

    /**
     * Gibt alle Todos zurück.
     *
     * @return eine unveränderliche Collection
     */
    public Collection<Todo> findAll() {
        return repo.findAll().stream()
          .map(mapper::map)
          .collect(toList());
    }

    /**
     * Durchsucht die Todos nach einer ID.
     *
     * @param id die ID
     * @return das Suchergebnis
     */
    public Optional<Todo> findById(long id) {
        return repo.findById(id)
          .map(mapper::map);
    }

    /**
     * Fügt ein Item in den Datenbestand hinzu. Dabei wird eine ID generiert.
     *
     * @param item das anzulegende Item (ohne ID)
     * @return das gespeicherte Item (mit ID)
     * @throws IllegalArgumentException wenn das Item null oder die ID bereits belegt ist
     */
    public Todo create(Todo item) {
        if (null == item || null != item.id()) {
            throw new IllegalArgumentException("item must exist without any id");
        }
        return mapper.map(repo.save(mapper.map(item)));
    }

    /**
     * Aktualisiert ein Item im Datenbestand.
     *
     * @param item das zu ändernde Item mit ID
     * @throws IllegalArgumentException
     * wenn das Item oder dessen ID nicht belegt ist
     * @throws NotFoundException
     * wenn das Element mit der ID nicht gefunden wird
     */
    public void update(Todo item) {
        if (null == item || null == item.id()) {
            throw new IllegalArgumentException("item must exist with an id");
        }
        // remove separat, um nicht neue Einträge hinzuzufügen (put allein würde auch ersetzen)
        if (repo.existsById(item.id())) {
            repo.save(mapper.map(item));
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Entfernt ein Item aus dem Datenbestand.
     *
     * @param id die ID des zu löschenden Items
     * @throws NotFoundException
     * wenn das Element mit der ID nicht gefunden wird
     */
    public void delete(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

}
