package com.baeldung.reactive.template;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.baeldung.reactive.model.Account;

import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class AccountTemplateOperationsUnitTest {

    @InjectMocks
    AccountTemplateOperations accountTemplateOperations;

    @Mock
    ReactiveMongoTemplate reactiveMangoTemplate;
    
    @Test
    public void givenAccountId_whenFindById_thenReturnedFromReactiveMangoTemplate() {
    	String accountId="ACC-123";
        accountTemplateOperations.findById(accountId);
        verify(reactiveMangoTemplate).findById(accountId,Account.class);
    }
    
    @Test
    public void givenAccountId_whenFindAll_thenReturnedFromReactiveMangoTemplate() {
        accountTemplateOperations.findAll();
        verify(reactiveMangoTemplate).findAll(Account.class);
    }
    
    @Test
    public void givenAccountId_whenSave_thenSavedUsingReactiveMangoTemplate() {
    	Account account=mock(Account.class);
    	Mono<Account> monoAccount=Mono.just(account);
        accountTemplateOperations.save(monoAccount);
        verify(reactiveMangoTemplate).save(monoAccount);
    }
    
    @Test
    public void givenAccountId_whenDeleteAll_thenRemovedUsingReactiveMangoTemplate() {
    	accountTemplateOperations.deleteAll();
        verify(reactiveMangoTemplate).remove(Account.class);
    }

}