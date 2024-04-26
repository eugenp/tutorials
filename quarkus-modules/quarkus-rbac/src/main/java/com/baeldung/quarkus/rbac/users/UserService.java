package com.baeldung.quarkus.rbac.users;

import com.baeldung.quarkus.rbac.api.UserDto;
import com.baeldung.quarkus.rbac.users.errors.EntityNotFoundException;
import com.baeldung.quarkus.rbac.users.errors.InvalidRolesProvidedException;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    private final UserRepository repository;
    private final String issuer;

    public UserService(final UserRepository repository,
                       final @ConfigProperty(name = "mp.jwt.verify.issuer") String issuer)  {
        this.repository = repository;
        this.issuer = issuer;
    }

    public String generateJwtToken(final User user) {

        final Set<String> permissions = user.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::name)
                .collect(Collectors.toSet());

        return Jwt.issuer(issuer)
                .upn(user.getUsername())
                .groups(permissions)
                .expiresIn(3600)
                .claim(Claims.email_verified.name(), user.getEmail())
                .sign();
    }

    @Transactional
    public User createUser(final UserDto userDto) {

        final var roles = repository.findRoles(userDto.roles());

        if (roles.size() != userDto.roles().size()) {
            throw new InvalidRolesProvidedException("Unknown role provided");
        }

        final var user = new User();

        user.setUsername(userDto.username());
        user.setPassword(BcryptUtil.bcryptHash(userDto.password()));
        user.setRoles(new HashSet<>(roles));
        user.setEmail(userDto.email());

        repository.persist(user);

        return user;
    }

    public boolean checkUserCredentials(String username, String password) {
        final User user = findByUsername(username);
        return BcryptUtil.matches(password, user.getPassword());
    }

    public User findByUsername(String username) {
        return repository.find("username", username).firstResultOptional()
                         .orElseThrow(() -> new EntityNotFoundException(username));
    }

}
