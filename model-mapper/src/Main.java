import com.baeldung.model.User;
import com.baeldung.model.UserDTO;
import com.baeldung.model.UserList;
import com.baeldung.util.MapperUtil;
import com.baeldung.util.UserPropertyMap;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sasam0320
 * @date 4/18/2020
 */

public class Main {

    public static void main(String[] args) {

        //Instantiate ModelMapper

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new UserPropertyMap());

        // Mapping lists using TypeToken generic class

        List<Integer> integers = new ArrayList<Integer>();

        integers.add(1);
        integers.add(2);
        integers.add(3);

        List<Character> characters = mapper.map(integers, new TypeToken<List<Character>>() {}.getType());

        System.out.println("Character list: " + characters);

        // Mapping lists using generic type methods

        List<User> users = new ArrayList();
        users.add(new User("b100", "user1", "user1@baeldung.com", "111-222", "USER"));
        users.add(new User("b101", "user2", "user2@baeldung.com", "111-333", "USER"));
        users.add(new User("b102", "user3", "user3@baeldung.com", "111-444", "ADMIN"));

        List<UserDTO> userDtoList = MapperUtil.mapList(users, UserDTO.class);
        userDtoList.stream().map(userDto -> userDto.getEmail()).forEachOrdered(System.out::println);

        // Mapping lists using PropertyMap and Converter

        UserList userList = new UserList();
        userList.setUsers(users);
        UserDTO dto = new UserDTO();

        mapper.map(userList, dto);
        dto.getUsernames().forEach(System.out::println);

    }
}
