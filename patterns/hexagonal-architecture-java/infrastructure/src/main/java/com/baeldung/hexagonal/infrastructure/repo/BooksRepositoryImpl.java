package com.baeldung.hexagonal.infrastructure.repo;

import com.baeldung.hexagonal.domain.data.Book;
import com.baeldung.hexagonal.domain.repo.BooksRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.UUID;

@Slf4j
public class BooksRepositoryImpl implements BooksRepository {

    private static final String INSERT_BOOKS_SQL = "INSERT INTO books" + " (id, title, author, description) VALUES " + " (?, ?, ?, ?);";
    private static final String DELETE_BOOKS_SQL = "DELETE FROM books WHERE id = '%s'";

    @Override
    public void save(Book book) {
        try (Connection connection = DatabaseUtils.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKS_SQL)) {
            preparedStatement.setString(1, book.getId()
                .toString());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getDescription());

            log.info(preparedStatement.toString());
            preparedStatement.executeUpdate();
            log.info("Book {} created", book.getId()
                .toString());
        } catch (SQLException e) {
            log.error("SQL exception during save", e);
        }
    }

    @Override
    public void delete(UUID id) {
        try (Connection connection = DatabaseUtils.getConnection(); Statement statement = connection.createStatement();) {
            statement.execute(String.format(DELETE_BOOKS_SQL, id));
            log.info("Book {} deleted", id);
        } catch (SQLException e) {
            log.error("SQL exception during delete", e);
        }
    }

}
