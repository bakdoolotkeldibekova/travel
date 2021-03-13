package com.example.travel.boot;

import com.example.travel.dto.UserDTO;
import com.example.travel.entity.Role;
import com.example.travel.repository.RoleRepository;
import com.example.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
//        Role role = new Role("ROLE_SUPERADMIN");
//        roleRepository.save(role);
//        Role role1 = new Role("ROLE_MODERATOR");
//        roleRepository.save(role1);
//        Role role2 = new Role("ROLE_USER");
//        roleRepository.save(role2);
//
//        UserDTO userDTO = new UserDTO("Name", "Surname", "bakdoolotkeldibekova@gmail.com", "12345678");
//        userService.createUser(userDTO);
    }
}
