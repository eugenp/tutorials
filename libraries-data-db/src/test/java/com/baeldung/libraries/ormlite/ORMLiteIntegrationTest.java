package com.baeldung.libraries.ormlite;

import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ORMLiteIntegrationTest {
    private static JdbcPooledConnectionSource connectionSource;

    private static Dao<Library, Long> libraryDao;
    private static Dao<Book, Long> bookDao;

    @BeforeClass
    public static void setup() throws SQLException {
        connectionSource = new JdbcPooledConnectionSource("jdbc:h2:mem:myDb");
        TableUtils.createTableIfNotExists(connectionSource, Library.class);
        TableUtils.createTableIfNotExists(connectionSource, Address.class);
        TableUtils.createTableIfNotExists(connectionSource, Book.class);

        libraryDao = DaoManager.createDao(connectionSource, Library.class);

        bookDao = DaoManager.createDao(connectionSource, Book.class);
    }

    @Test
    public void givenDAO_whenCRUD_thenOk() throws SQLException {
        Library library = new Library();
        library.setName("My Library");
        libraryDao.create(library);

        Library result = libraryDao.queryForId(library.getLibraryId());
        assertEquals("My Library", result.getName());

        library.setName("My Other Library");
        libraryDao.update(library);

        libraryDao.delete(library);

    }

    @Test
    public void whenLoopDao_thenOk() throws SQLException {
        Library library1 = new Library();
        library1.setName("My Library");
        libraryDao.create(library1);

        Library library2 = new Library();
        library2.setName("My Other Library");
        libraryDao.create(library2);

        libraryDao.forEach(lib -> {
            System.out.println(lib.getName());
        });

    }

    @Test
    public void givenIterator_whenLoop_thenOk() throws SQLException, IOException {
        Library library1 = new Library();
        library1.setName("My Library");
        libraryDao.create(library1);

        Library library2 = new Library();
        library2.setName("My Other Library");
        libraryDao.create(library2);

        try (CloseableWrappedIterable<Library> wrappedIterable = libraryDao.getWrappedIterable()) {
            wrappedIterable.forEach(lib -> {
                System.out.println(lib.getName());
            });
        }

    }

    @Test
    public void givenCustomDao_whenSave_thenOk() throws SQLException, IOException {
        Library library = new Library();
        library.setName("My Library");

        LibraryDao customLibraryDao = DaoManager.createDao(connectionSource, Library.class);
        customLibraryDao.create(library);
        assertEquals(1, customLibraryDao.findByName("My Library")
            .size());
    }

    @Test
    public void whenSaveForeignField_thenOk() throws SQLException, IOException {
        Library library = new Library();
        library.setName("My Library");
        library.setAddress(new Address("Main Street nr 20"));
        libraryDao.create(library);

        Dao<Address, Long> addressDao = DaoManager.createDao(connectionSource, Address.class);
        assertEquals(1, addressDao.queryForEq("addressLine", "Main Street nr 20")
            .size());
    }

    @Test
    public void whenSaveForeignCollection_thenOk() throws SQLException, IOException {
        Library library = new Library();
        library.setName("My Library");
        libraryDao.create(library);
        libraryDao.refresh(library);
        library.getBooks()
            .add(new Book("1984"));

        Book book = new Book("It");
        book.setLibrary(library);
        bookDao.create(book);

        assertEquals(2, bookDao.queryForEq("library_id", library)
            .size());
    }

    @Test
    public void whenGetLibrariesWithMoreThanOneBook_thenOk() throws SQLException, IOException {
        Library library = new Library();
        library.setName("My Library");
        libraryDao.create(library);
        Library library2 = new Library();
        library2.setName("My Other Library");
        libraryDao.create(library2);

        libraryDao.refresh(library);
        libraryDao.refresh(library2);

        library.getBooks()
            .add(new Book("Book1"));
        library2.getBooks()
            .add(new Book("Book2"));
        library2.getBooks()
            .add(new Book("Book3"));

        List<Library> libraries = libraryDao.queryBuilder()
            .where()
            .in("libraryId", bookDao.queryBuilder()
                .selectColumns("library_id")
                .groupBy("library_id")
                .having("count(*) > 1"))
            .query();
        assertEquals(1, libraries.size());

    }

    @After
    public void clear() throws SQLException {
        TableUtils.clearTable(connectionSource, Library.class);
        TableUtils.clearTable(connectionSource, Book.class);
        TableUtils.clearTable(connectionSource, Address.class);
    }

    @AfterClass
    public static void tearDown() throws SQLException, IOException {
        connectionSource.close();
    }
}
