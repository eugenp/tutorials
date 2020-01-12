package com.baeldung.dddhexagonalquickexample.infraestructure.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.dddhexagonalquickexample.domain.CarStore;
import com.baeldung.dddhexagonalquickexample.infraestructure.adapter.CrudCarStoreRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CarStoreRepository.class, CrudCarStoreRepository.class})
public class CarStoreRepositoryTest{

	private CrudCarStoreRepository carStoreRepository;
    private CarStoreRepository repository;
	
    @BeforeEach
    void setUp(){
    	carStoreRepository = mock(CrudCarStoreRepository.class);
    	repository = new CarStoreRepository();
    	repository.setCarStoreRepository(carStoreRepository);
    }
    
    @Test
    void whenFindStoreById_thenOk() throws Exception {
    	// given
    	Long idStore = 1L;
    	CarStore store = new CarStore();
    	doReturn(Optional.of(store)).when(carStoreRepository).findById(idStore);

        // when
    	CarStore result = repository.findStoreById(idStore);

        // then
        verify(carStoreRepository, times(1)).findById(idStore);
        assertThat(result).isEqualTo(store);
    }
    
    @Test
    void whenFindStoreById_thenNull() throws Exception {
    	// given
    	Long idStore = 1L;
    	doReturn(Optional.empty()).when(carStoreRepository).findById(idStore);

        // when/then
    	assertThrows(NoSuchElementException.class, () -> {
    		repository.findStoreById(idStore);
        });
    }
    
    @Test
    void whenSaveStore_thenOk() throws Exception {
    	// given
    	CarStore store = new CarStore();
    	doReturn(store).when(carStoreRepository).save(store);

        // when
    	repository.saveStore(store);

        // then
        verify(carStoreRepository, times(1)).save(store);
    }
}
