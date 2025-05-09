package com.gamexd.configuration;

import com.gamexd.domain.entity.Role;
import com.gamexd.domain.entity.User;
import com.gamexd.repository.RoleRepository;
import com.gamexd.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
    @Value("${ADMIN_PASSWORD}")
    String rawPassword;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        var userAdmin = userRepository.findByEmail("admin");

        userAdmin.ifPresentOrElse(
                (user) -> {
                    System.out.println("admin já cadastrado!");
                },
                () -> {
                    if (rawPassword == null || rawPassword.isBlank()) {
                        throw new IllegalStateException("ADMIN_PASSWORD environment variable is not set.");
                    }
                    var user = new User();
                    user.setEmail("admin");
                    user.setPassword(passwordEncoder.encode(rawPassword));
                    user.setRole(Set.of(roleAdmin));
                    user.setEnabled(true);
                    userRepository.save(user);
                }
        );
    }
}
