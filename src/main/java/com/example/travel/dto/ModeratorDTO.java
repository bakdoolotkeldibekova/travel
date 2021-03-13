package com.example.travel.dto;

import com.example.travel.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModeratorDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDateTime dateOfBirth;
    private Organization organization;
    private String description;
}
