package com.baeldung.cucumber.books;

import java.util.Locale;

import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableTransformer;

public class BookStoreRegistryConfigurer implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineDataTableType(new DataTableType(BookCatalog.class, new BookTableTransformer()));
        
    }
    
    private static class BookTableTransformer implements TableTransformer<BookCatalog> {

        @Override
        public BookCatalog transform(DataTable table) throws Throwable {

            BookCatalog catalog = new BookCatalog();
            
            table.cells()
                .stream()
                .skip(1)        // Skip header row
                .map(fields -> new Book(fields.get(0), fields.get(1)))
                .forEach(catalog::addBook);
            
            System.out.println(catalog);
            
            return catalog;
        }
        
    }

}
