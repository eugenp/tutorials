package com.baeldung.modelmapper;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;


/**
 * This class has test methods of mapping Integer to Character list,
 * mapping users list to DTO list using MapperUtil custom type method and property mapping using converter class
 *
 * @author Sasa Milenkovic
 */
public class UsersListMappingUnitTest {

    private ModelMapper modelMapper;
    private List<User> users;

    @Before
    public void init() {

        modelMapper = new ModelMapper();

        TypeMap<UserList, UserListDTO> typeMap = modelMapper.createTypeMap(UserList.class, UserListDTO.class);

        typeMap.addMappings(mapper -> mapper.using(new UsersListConverter())
                .map(UserList::getUsers, UserListDTO::setUsernames));

        users = new ArrayList();
        users.add(new User("b100", "user1", "user1@baeldung.com", "111-222", "USER"));
        users.add(new User("b101", "user2", "user2@baeldung.com", "111-333", "USER"));
        users.add(new User("b102", "user3", "user3@baeldung.com", "111-444", "ADMIN"));

    }

    @Test
    public void whenInteger_thenMapToCharacter() {

        List<Integer> integers = new ArrayList<Integer>();

        integers.add(1);
        integers.add(2);
        integers.add(3);

        List<Character> characters = modelMapper.map(integers, new TypeToken<List<Character>>() {
        }.getType());

        assertThat(characters, hasItems('1', '2', '3'));

    }

    @Test
    public void givenUsersList_whenUseGenericType_thenMapToUserDTO() {

        // Mapping lists using custom (generic) type mapping

        List<UserDTO> userDtoList = MapperUtil.mapList(users, UserDTO.class);

        assertThat(userDtoList, Matchers.<UserDTO>hasItem(
                Matchers.both(hasProperty("userId", equalTo("b100")))
                        .and(hasProperty("email", equalTo("user1@baeldung.com")))
                        .and(hasProperty("username", equalTo("user1")))));


    }

    @Test
    public void givenUsersList_whenUseConverter_thenMapToUsernames() {

        // Mapping lists using property mapping and converter

        UserList userList = new UserList();
        userList.setUsers(users);
        UserListDTO dtos = new UserListDTO();
        modelMapper.map(userList, dtos);

        assertThat(dtos.getUsernames(), hasItems("user1", "user2", "user3"));

    }

}