package com.example.travel.dto;

import com.example.travel.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Organization organization;
    private String phoneNumber;
}
