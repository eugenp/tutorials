package com.baeldung.architecture.hexagonal.user.application;

import com.baeldung.architecture.hexagonal.user.core.model.AddUserCommand;
import com.baeldung.architecture.hexagonal.user.core.model.RemoveUserCommand;
import com.baeldung.architecture.hexagonal.user.core.ports.incoming.AddUser;
import com.baeldung.architecture.hexagonal.user.core.ports.incoming.RemoveUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final AddUser addUser;

    private final RemoveUser removeUser;

    public UserController(AddUser addUser, RemoveUser removeUser) {
        this.addUser = addUser;
        this.removeUser = removeUser;
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody AddUserCommand addUserCommand) {
        addUser.handle(addUserCommand);
        return new ResponseEntity<>("User has been added", HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody RemoveUserCommand removeUserCommand) {
        removeUser.handle(removeUserCommand);
        return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
    }
}
