package org.baeldung.config;

import org.baeldung.entity.Task;
import org.baeldung.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 
 * @author felipereis
 *
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... strings) throws Exception {
        // Force authentication to avoid error when calling repository methods
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("admin", "any password", AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

        this.taskRepository.save(new Task("Send a fax", "pam"));
        this.taskRepository.save(new Task("Print a document", "pam"));
        this.taskRepository.save(new Task("Answer the phone", "pam"));
        this.taskRepository.save(new Task("Call a client", "jim"));
        this.taskRepository.save(new Task("Organize a meeting", "michael"));
    }
}