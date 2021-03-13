package com.example.travel.controller;

import com.example.travel.entity.ResponseMessage;
import com.example.travel.entity.Role;
import com.example.travel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseMessage create(@RequestBody String name){
        return roleService.create(name);
    }

    @GetMapping
    public List<Role> getAll(){
        return roleService.getAll();
    }

    @GetMapping("/id/{id}")
    public ResponseMessage getById(@PathVariable Long id){
        return roleService.getById(id);
    }
}
