package com.example.travel.service;

import com.example.travel.entity.ResponseMessage;
import com.example.travel.entity.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
    ResponseMessage create(String name);
    ResponseMessage getById(Long id);
    List<Role> getAll();
}