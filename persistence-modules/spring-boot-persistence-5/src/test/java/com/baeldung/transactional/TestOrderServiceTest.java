package com.baeldung.transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { Application.class, OrderService.class, TestOrderRepository.class })
class TestOrderServiceTest {

    @Autowired
    private OrderService underTest;

    @Autowired
    private TestOrderRepository repository;

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @Test
    void givenPublicTransactionalMethod_whenCallingIt_thenShouldRollbackOnException() {
        assertThat(repository.findAll()).isEmpty();

        assertThatThrownBy(() -> underTest.createOrderPublic(new TestOrder())).isNotNull();

        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    void givenPackagePrivateTransactionalMethod_whenCallingIt_thenShouldRollbackOnException() {
        assertThat(repository.findAll()).isEmpty();

        assertThatThrownBy(() -> underTest.createOrderPackagePrivate(new TestOrder())).isNotNull();

        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    void givenProtectedTransactionalMethod_whenCallingIt_thenShouldRollbackOnException() {
        assertThat(repository.findAll()).isEmpty();

        assertThatThrownBy(() -> underTest.createOrderProtected(new TestOrder())).isNotNull();

        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    void givenPrivateTransactionalMethod_whenCallingIt_thenShouldNotRollbackOnException() {
        assertThat(repository.findAll()).isEmpty();

        assertThatThrownBy(() -> underTest.callPrivate(new TestOrder())).isNotNull();

        assertThat(repository.findAll()).hasSize(1);
    }
}
