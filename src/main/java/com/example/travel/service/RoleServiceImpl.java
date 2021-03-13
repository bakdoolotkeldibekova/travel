package com.example.travel.service;

import com.example.travel.entity.ResponseMessage;
import com.example.travel.entity.Role;
import com.example.travel.exception.ResourceNotFoundException;
import com.example.travel.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseMessage create(String name) {
        Role role = new Role(name);
        return new ResponseMessage(HttpStatus.OK.value(), "ok!", roleRepository.save(role));
   }

    @Override
    public ResponseMessage getById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Role id " + id +" not found!"));
        return new ResponseMessage(HttpStatus.OK.value(), "ok!", role);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}