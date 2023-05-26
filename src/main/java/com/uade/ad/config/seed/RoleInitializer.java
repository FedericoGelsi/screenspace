package com.uade.ad.config.seed;

import com.uade.ad.model.Constants.RolesConstants;
import com.uade.ad.model.Roles;
import com.uade.ad.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        Roles user = new Roles();
        user.setName(RolesConstants.ROLE_CLIENT);
        Roles admin = new Roles();
        admin.setName(RolesConstants.ROLE_ADMIN);

        List<Roles> roles = Arrays.asList(user,admin);
        for (Roles role : roles) {
            if (!roleRepository.existsByName(role.getName())) {
                roleRepository.save(role);
            }
        }
    }
}

