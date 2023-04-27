package com.ossovita.insurancecampaignapi.utilities;

import com.ossovita.insurancecampaignapi.entity.User;
import com.ossovita.insurancecampaignapi.entity.UserRole;
import com.ossovita.insurancecampaignapi.repository.UserRepository;
import com.ossovita.insurancecampaignapi.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        //check if not saved
        if (userRoleRepository.findAll().isEmpty()) {
            //save user roles into db
            UserRole userRoleAdmin = UserRole.builder()
                    .userRole("Admin")
                    .build();

            UserRole userRoleCompany = UserRole.builder()
                    .userRole("Company")
                    .build();
            userRoleRepository.save(userRoleAdmin);
            userRoleRepository.save(userRoleCompany);
            log.info("UserService | My CommandLineRunner | user role data initialized");

            //save demo admin into db
            User user = User.builder()
                    .userEmail("admin@gmail.com")
                    .userPassword(passwordEncoder.encode("User.123"))
                    .userFirstName("Demo")
                    .userLastName("Admin")
                    .userRoleId(1)
                    .enabled(true)
                    .locked(false)
                    .build();
            log.info("UserService | My CommandLineRunner | demo admin initialized");
            userRepository.save(user);
        }
    }

}
