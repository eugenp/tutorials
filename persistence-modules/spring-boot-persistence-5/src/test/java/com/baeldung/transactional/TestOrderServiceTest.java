package com.baeldung.transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = { Application.class, TestOrderServiceTest.TestOrderService.class, TestOrderRepository.class })
class TestOrderServiceTest {

    @Autowired
    private TestOrderService underTest;

    @Autowired
    private TestOrderRepository testOrderRepository;

    @Service
    static class TestOrderService {

        @Autowired
        private TestOrderRepository repository;

        @Transactional
        public void createOrderPublic(TestOrder order) {
            repository.save(order);
            throw new RuntimeException("Rollback createOrderPublic");
        }

        @Transactional
        void createOrderPackagePrivate(TestOrder order) {
            repository.save(order);
            throw new RuntimeException("Rollback createOrderPackagePrivate");
        }

        @Transactional
        protected void createOrderProtected(TestOrder order) {
            repository.save(order);
            throw new RuntimeException("Rollback createOrderProtected");
        }

        @Transactional
        private void createOrderPrivate(TestOrder order) {
            repository.save(order);
            throw new RuntimeException("Rollback createOrderPrivate");
        }

        public void callPrivate(TestOrder order) {
            createOrderPrivate(order);
        }
    }

    @AfterEach
    void afterEach() {
        testOrderRepository.deleteAll();
    }

    @Test
    void givenPublicTransactionalMethod_whenCallingIt_thenShouldRollbackOnException() {
        assertThat(testOrderRepository.findAll()).isEmpty();

        assertThatThrownBy(() -> underTest.createOrderPublic(new TestOrder())).isNotNull();

        assertThat(testOrderRepository.findAll()).isEmpty();
    }

    @Test
    void givenPackagePrivateTransactionalMethod_whenCallingIt_thenShouldRollbackOnException() {
        assertThat(testOrderRepository.findAll()).isEmpty();

        assertThatThrownBy(() -> underTest.createOrderPackagePrivate(new TestOrder())).isNotNull();

        assertThat(testOrderRepository.findAll()).isEmpty();
    }

    @Test
    void givenProtectedTransactionalMethod_whenCallingIt_thenShouldRollbackOnException() {
        assertThat(testOrderRepository.findAll()).isEmpty();

        assertThatThrownBy(() -> underTest.createOrderProtected(new TestOrder())).isNotNull();

        assertThat(testOrderRepository.findAll()).isEmpty();
    }

    @Test
    void givenPrivateTransactionalMethod_whenCallingIt_thenShouldNotRollbackOnException() {
        assertThat(testOrderRepository.findAll()).isEmpty();

        assertThatThrownBy(() -> underTest.callPrivate(new TestOrder())).isNotNull();

        assertThat(testOrderRepository.findAll()).hasSize(1);
    }
}
