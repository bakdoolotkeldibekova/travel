package com.example.travel.service;

import com.example.travel.entity.Role;
import com.example.travel.entity.User;
import com.example.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User userAccount = userAccountRepository.findByEmailAndActiveAndDeleted(login, true, false);
        List<Role> roles = new ArrayList<>();
        roles.add(userAccount.getRole());
        return new org.springframework.security.core.userdetails.User(userAccount.getEmail(), userAccount.getPassword(), roles);
    }
}