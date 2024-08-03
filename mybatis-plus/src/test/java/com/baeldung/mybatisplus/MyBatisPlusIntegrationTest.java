package com.baeldung.mybatisplus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.mybatisplus.entity.Client;
import com.baeldung.mybatisplus.idgenerator.TimestampIdGenerator;
import com.baeldung.mybatisplus.service.ClientService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyBatisPlusIntegrationTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private TimestampIdGenerator timestampIdGenerator;

    @Test
    @Order(1)
    public void whenSaveIsInvoked_ThenClientIsInserted() {
        Client client = new Client();
        client.setFirstName("Anshul");
        client.setLastName("Bansal");
        client.setEmail("anshul.bansal@example.com");
        clientService.save(client);

        assertNotNull(client.getId());
    }

    @Test
    @Order(2)
    public void whenGetByIdIsInvoked_ThenClientIsRead() {
        assertNotNull(clientService.getById(2));
    }

    @Test
    @Order(3)
    public void whenListIsInvoked_ThenClientsAreRead() {
        assertEquals(6, clientService.list()
            .size());
    }

    @Test
    @Order(4)
    public void whenUsingQueryMappers_ThenClientsAreRead() {
        Map<String, Object> map = Map.of("id", 2, "first_name", "Laxman");

        QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
        clientQueryWrapper.allEq(map);
        assertNotNull(clientService.getBaseMapper()
            .selectOne(clientQueryWrapper));

        LambdaQueryWrapper<Client> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Client::getId, 3);
        assertNotNull(clientService.getBaseMapper()
            .selectOne(lambdaQueryWrapper));

        QueryChainWrapper<Client> queryChainWrapper = clientService.query();
        queryChainWrapper.allEq(map);
        assertNotNull(clientService.getBaseMapper()
            .selectOne(queryChainWrapper.getWrapper()));
    }

    @Test
    @Order(5)
    public void givenExistingClient_whenUpdateIsInvoked_ThenClientIsUpdated() {
        Client client = clientService.getById(2);
        client.setEmail("anshul.bansal@baeldung.com");

        clientService.updateById(client);

        assertEquals("anshul.bansal@baeldung.com", clientService.getById(2)
            .getEmail());
    }

    @Test
    @Order(6)
    public void WhenUsingLambdaUpdateWrapper_ThenClientsAreUpdated() {
        LambdaUpdateWrapper<Client> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Client::getEmail, "x@e.com");

        assertTrue(clientService.update(lambdaUpdateWrapper));

        QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
        clientQueryWrapper.allEq(Map.of("email", "x@e.com"));

        assertTrue(clientService.list(clientQueryWrapper)
            .size() > 5);
    }

    @Test
    @Order(7)
    public void whenRemoveByIdIsInvoked_ThenClientIsDeleted() {
        clientService.removeById(1);

        assertNull(clientService.getById(1));
    }

    @Test
    @Order(7)
    public void givenColumnMap_whenRemoveByMapIsInvoked_ThenClientsAreDeleted() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("email", "x@e.com");

        clientService.removeByMap(columnMap);

        assertEquals(0, clientService.list()
            .size());
    }

    @Test
    @Order(8)
    public void whenSaveBatchIsInvoked_ThenClientsAreInsertedInBatch() {
        Client client2 = new Client();
        client2.setFirstName("Harry");

        Client client3 = new Client();
        client3.setFirstName("Ron");

        Client client4 = new Client();
        client4.setFirstName("Hermione");

        clientService.saveBatch(Arrays.asList(client2, client3, client4));

        assertNotNull(client2.getId());
        assertNotNull(client3.getId());
        assertNotNull(client4.getId());
    }

    @Test
    @Order(9)
    public void givenExistingClients_whenPageIsInvoked_ThenResultsArePaginated() {
        Client client2 = new Client();
        client2.setFirstName("John");

        Client client3 = new Client();
        client3.setFirstName("Smith");

        Client client4 = new Client();
        client4.setFirstName("Charlie");

        clientService.saveBatch(Arrays.asList(client2, client3, client4));

        Page<Client> page = Page.of(2, 3);

        assertEquals(3, clientService.page(page, null)
            .getRecords()
            .size());
    }

    @Test
    @Order(10)
    public void whenSelectListIsInvoked_ThenResultsAreStreamed() {
        clientService.getBaseMapper()
            .selectList(Wrappers.emptyWrapper(), resultContext ->
                assertNotNull(resultContext.getResultObject()));
    }

    @Test
    @Order(11)
    public void givenExistingClient_WhenUsingRemovedByIdWithDeleted_ThenClientIsLogicallyDeleted() {
        Map<String, String> map = Map.of("first_name", "Harry");

        QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
        clientQueryWrapper.allEq(map);
        Client harry = clientService.getBaseMapper()
            .selectOne(clientQueryWrapper);

        clientService.removeById(harry);

        assertNull(clientService.getById(harry.getId()));
    }

    @Test
    @Order(12)
    public void whenNextIdIsInvoked_ThenCustomIdGeneratorIsUsed() {
        Client harry = new Client();
        harry.setId(timestampIdGenerator.nextId(harry));
        harry.setFirstName("Harry");
        clientService.save(harry);

        assertTrue(System.nanoTime() > harry.getId());
    }

}