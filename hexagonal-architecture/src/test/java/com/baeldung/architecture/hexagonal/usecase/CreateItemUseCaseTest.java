package com.baeldung.architecture.hexagonal.usecase;

import org.junit.jupiter.api.*;

import com.baeldung.architecture.hexagonal.adapter.persistence.*;

class CreateItemUseCaseTest
{
    @Test
    void createItem()
    {
        CreateItemUseCase createItemUseCase = new CreateItemUseCase(new ItemRepositoryImplSet());

        Assertions.assertDoesNotThrow(() -> createItemUseCase.createItem("item1"));
    }
}