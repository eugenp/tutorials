package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.application.IAddMethods;
import com.baeldung.hexagonal.application.IRemoveMethods;
import com.baeldung.hexagonal.domain.Documentation;
import com.baeldung.hexagonal.repository.DocumentationRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service public class DocumentationService implements IAddMethods, IRemoveMethods {

        private DocumentationRepository repository;

        public DocumentationService(DocumentationRepository repository) {
                this.repository = repository;
        }

        @Override public void add(String className, String methodName, String methodDescription) {
                Documentation documentation = repository.load(className).orElseThrow(NoSuchElementException::new);
                documentation.addMethodToDocumentation(methodName, methodDescription);
                repository.save(documentation);
        }

        @Override public boolean remove(String className, String methodName) {
                Documentation documentation = repository.load(className).orElseThrow(NoSuchElementException::new);
                if (documentation.removeMethodFromDocumentation(methodName)) {
                        repository.save(documentation);
                        return true;
                }
                return false;
        }
}
