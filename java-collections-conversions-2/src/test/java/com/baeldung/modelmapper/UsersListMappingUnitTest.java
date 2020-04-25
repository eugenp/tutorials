package com.baeldung.modelmapper;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


/**
 * This class has test methods of mapping Integer to Character list,
 * mapping user list to DTO list using MapperUtil generic methods and Converter
 *
 * @author Sasa Milenkovic
 */
public class UsersListMappingUnitTest {

    private ModelMapper modelMapper;
    private List<User> users;

    @Before
    public void init() {

        modelMapper = new ModelMapper();
        modelMapper.addMappings(new UserPropertyMap());
        users = new ArrayList();
        users.add(new User("b100", "user1", "user1@baeldung.com", "111-222", "USER"));
        users.add(new User("b101", "user2", "user2@baeldung.com", "111-333", "USER"));
        users.add(new User("b102", "user3", "user3@baeldung.com", "111-444", "ADMIN"));

    }

    @Test
    public void whenMapIntegerToCharList() {

        List<Integer> integers = new ArrayList<Integer>();

        integers.add(1);
        integers.add(2);
        integers.add(3);

        List<Character> characters = modelMapper.map(integers, new TypeToken<List<Character>>() {
        }.getType());

        assertThat(characters, hasItems('1', '2', '3'));

    }

    @Test
    public void givenUsersList_whenUseGenericType_thenMapToDto() {

        // Mapping lists using generic type methods

        List<UserDTO> userDtoList = MapperUtil.mapList(users, UserDTO.class);

        assertThat(userDtoList, Matchers.<UserDTO>hasItem(
                Matchers.both(hasProperty("userId", equalTo("b100")))
                        .and(hasProperty("email", equalTo("user1@baeldung.com")))
                        .and(hasProperty("username", equalTo("user1")))));

        // Mapping lists using PropertyMap and Converter

        UserList userList = new UserList();
        userList.setUsers(users);
        UserListDTO dto = new UserListDTO();
        modelMapper.map(userList, dto);

        assertNotNull(dto);
        assertThat(dto, Matchers.hasProperty("usernames"));
        assertThat(dto.getUsernames(), hasSize(3));

    }

}