package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.domain.user.User;
import com.baeldung.architecture.hexagonal.port.UserCreationService;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/users")
public class UserResources {

    private UserCreationService userCreationService;

    public UserResources(UserCreationService userCreationService) {
        this.userCreationService = userCreationService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public UserDto createUser(UserDto userDto) {
        User createdUser = userCreationService.createUser(userDto.getUserId(), userDto.getFirstName(),
                userDto.getLastName());
        UserDto createdUserDto = new UserDto();
        createdUserDto.setUserId(createdUser.getUserId());
        createdUserDto.setFirstName(createdUser.getFirstName());
        createdUserDto.setLastName(createdUser.getLastName());
        return createdUserDto;
    }
}
