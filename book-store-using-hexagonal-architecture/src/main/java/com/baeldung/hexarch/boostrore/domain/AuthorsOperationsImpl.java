package com.baeldung.hexarch.boostrore.domain;

import com.baeldung.hexarch.boostrore.model.Author;
import com.baeldung.hexarch.boostrore.repository.AuthorsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorsOperationsImpl implements AuthorsOperation {
    private final AuthorsRepository authorsRepository;
    @Override
    public Author create(String firstName, String lastName, String emailId) {
        Author author = new Author();
        author.setEmailId(emailId);
        author.setFirstName(firstName);
        author.setLastName(lastName);

        return authorsRepository.save(author);
    }

    @Override
    public Author getAuthorByEmailId(String emailId) {
        return authorsRepository.findByEmailId(emailId).get();
    }

    @Override
    public Author get(long id) {
        return authorsRepository.findById(id).get();
    }

    @Override
    public void delete(String emailId) {
        authorsRepository.deleteByEmailId(emailId);
    }

    @Override
    public void delete(long id) {
        authorsRepository.deleteById(id);
    }
}
